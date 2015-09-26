# mALIGNa

## Introduction

mALIGNa is a program for aligning documents on the sentence level. 
It contains implementations of a few groups of alignment algorithms -
algorithms based exclusively on the sentence length (Gale and Church, Brown), 
algorithms based on the connections between words (Moore's algorithm), 
as well as any variation and intersection of these algorithms. 
The aim of alignment is to obtain a bilingual corpus. 
It can be used for creating translation memories, translation by analogy, 
modelling probabilistic dictionaries and other applications.
More information about maligna can be found in an article which I co-authored 
(see Resources section).

## Library

mALIGNa library is available on Maven central. See [pom.xml](maligna/pom.xml) for details.

## Requirements

JVM 1.6 is required to run the program. 
To build the program both JDK 1.6 and Maven are required. 
Theoretically, the program is platform independent (like Java), 
but it was tested only on Linux and Windows.

## Running

Aligning documents consists of several stages, which may be performed 
in different ways. Therefore, the text interface of the program is divided 
into several independent commands. A common feature of the commands is 
that they read input text from standard input and write the results 
to standard output, always using the native format .al. 
Therefore these programs act as filters and may be combined in a pipe using |. 
Below is a brief description of each command, you can get more information 
about exact parameters of individual commands using the option --help. 
At the end of this document, there is a complete example of text 
alignment using several commands connected in a pipeline.

### The 'parse' command

The parse command is used to convert an external format 
to the native format .al. It also allows you to combine several documents 
into one, by giving a list of files as arguments. 
It accepts input files as arguments and writes the result to standard output. 
This command can only occur at the beginning of the pipeline.

### The 'format' command

The 'format' command is used to convert the native format .al 
to an external format. It reads data from standard input and writes the result 
to the files given as arguments. This command can only occur at the end 
of the pipeline.

### The 'align' command

Align command. Segments of each of the input mappings are aligned independently,
thanks to which alignment can be performed at different levels of accuracy 
(document, paragraph, sentence), by performing consecutive alignment operations,
and then dividing the results into smaller and smaller segments. 
Filter; may be used at any point of the pipeline.

### The 'modify' command

This command performs modifications on every mapping, replacing source and 
target lists of segments with other lists of segments. 
Both the amount of the segments and their contents may be changed 
(e.g. merge segments in one, split segment into more segments or 
remove unnecessary whitespace in each segment). 
Filter; may be used at any point of the pipeline.

### The 'select' command

Basing on certain criteria, chooses from the input list of mappings only 
some mappings and writes them to standard output. 
Eg. using this command you can choose only the most probable mappings 
or only 1 - 1 mappings. Filter; may be used at any point of the pipeline.

### 3.6 The 'compare' command

This command is used for comparing two files of alignments with each other, 
provided as arguments, returning the degree of their similarity 
(precision, recall) and the differences occurring. 
Used for test purposes outside the pipeline.

### The 'model' command

This command manipulates translation, language and length models. 
Currently not fully implemented.
Used outside the pipeline.

### The 'macro' command

Executes a set of predefined filtering commands, like doing a Moore alignment.
Created to simplify complex operations and improve the performance.
Filter; may be used at any point of the pipeline.


### The 'test' command

Runs the program's automatic tests.

### Examples

Below I have given examples of pipelines of commands that that should be used 
to align two documents in .txt files and write the results as two .txt files. 
The output documents will contain the same number of sentences, 
one per line, and sentences of corresponding numbers should be mutual 
translations. The commands given below will do everything required, 
however it is worth remembering that sometimes it's better to preserve 
the intermediate results of the operations of each command in temporary files 
instead of redirecting them directly to the next command for debugging purposes. 
This example should be executed in the main directory of the project. 
Ready to run example scripts can be found in the examples/scripts directory.

    bin/maligna parse -c txt example/txt/poznan-pl.txt example/txt/poznan-de.txt | \
    bin/maligna modify -c split-sentence | \
    bin/maligna modify -c trim | \
    bin/maligna align -c viterbi -a poisson -n word -s iterative-band | \
    bin/maligna select -c one-to-one | \
    bin/maligna format -c txt poznan-pl-align.txt poznan-de-align.txt

Another interesting case is alignment using Moore's algorithm, 
which requires a properly aligned corpus to build a translation model. 
To do this you must perform several groups of commands.

Split a text into sentences and clean them up:

    bin/maligna parse -c txt example/txt/poznan-pl.txt example/txt/poznan-de.txt | \
    bin/maligna modify -c split-sentence | \
    bin/maligna modify -c trim > \
    poznan-split.al

Align using sentence length-based algorithm (Brown, Gale and Church) 
and select most probable alignments:

    cat poznan-split.al | \
    bin/maligna align -c viterbi -a poisson -n word -s iterative-band | \
    bin/maligna select -c one-to-one | \
    bin/maligna select -c fraction -f 0.85 > \
    poznan-align-length.al

Finally align using Moore's algorithm:

    cat poznan-split.al | \
    bin/maligna align -c viterbi -a translation -n word -s iterative-band -t poznan-align-length.al > \
    poznan-align.al

## Formats

Most commands expect input and output in the native format .al. 
To use a different format, you must parse the input using the parse command. 
To get the result in another format, you need to format it using 
the format command.

### The .al format

This is the native format for alignment files. 
Contains all necessary information for each mapping: 
lists of source and target segments and mapping score (-log(probability)).
If mapping score is equal to "-INF" then all commands ignore this mapping,
no operation is performed on it. This feature can be used to mark manual, 
human-aligned fragments to be preserved in the result.  

### The .tmx format

The standard format for translation memories, supported by many tools. 
Both an input and output format. For the full specification see
<a href="http://www.lisa.org/standards/tmx/tmx.html">http://www.lisa.org/standards/tmx/tmx.html</a>.

### The .txt format

Plain text, in the form of two files in two languages. 
On input the whole file is treated as one segment. 
On output, successive lines of files correspond to mappings and are 
mutual translations (which implies that numbers of lines are equal).

### The presentation format

An output format which presents an alignment in a human readable manner.

## Algorithms

In the program a few alignment algorithms were implemented, and thanks to the 
flexibility of the code it is easy to add new algorithms, 
modify the existing ones and join their results together.

### Gale and Church algorithm

This is a fast bilingual text alignment algorithm. The algorithm counts the 
probability of each possible mapping, which depends on ratio of lengths of 
sentences in each language. Next it finds alignment for the whole text with 
maximum probability (calculated as a product of individual mapping probabilities). 
Details can be found in the Gale and Church article, referenced in resources.

### Moore's algorithm

This is a modern algorithm based not only on the length of sentences but also on 
their contents. 
The first phase of operation of this algorithm is alignment based on length 
(for example using Gale and Church algorithm described above). 
Next, from this alignment only 1 - 1 mappings alignments are selected, 
and from them only the most probable ones (e.g. 80% probability) are selected. 
In this manner a relatively well aligned corpus is yielded. 
Later a translation model (IBM Model 1) and unigram language models are built 
based on this corpus. 
In the final alignment, probability of the translation of the source sentence to 
the target sentence is taken into account (calculated on the basis of the 
translation model and the language models). 
Details can be found in the Moore article, referenced in the resources.

## Resources

1. A new tool for the bilingual text aligning at the sentence level, 
   Krzysztof Jassem, Jarek Lipski, Proceedings of Intelligent Information Systems Conference 2008, Zakopane, Poland,
   http://iis.ipipan.waw.pl/2008/proceedings/iis08-27.pdf
2. A Program for Aligning Sentences in Bilingual Corpora, William A. Gale, Kenneth Ward Church
3. Fast and Accurate Sentence Alignment of Bilingual Corpora, Robert C. Moore

## Authors

* Jarek Lipski - creation of the project, design and programming
* Jimmy O'Regan - translation of readme file to English

## Thanks

I wrote this program during my studies in Machine Translation, 
and later I expanded it as practical part of a master's thesis.
Currently it is being developed as an open-source project and used by
various other projects. Happy aligning!

&nbsp;&nbsp;&nbsp;&nbsp;-- Jarek Lipski

