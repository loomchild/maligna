# ChangeLog

## version 0.0 2005-11

* Project inception - first version based on Gale and Church article.

## version 0.9 2005-12-16

* Functional version used  by me in sentence alignment for Exprimo project; hard to use.

## version 1.0 2006-01-30

* First public release to the users. A little clumsy text interface, added documentation and examples.

## version 1.9 2006-02

* Conceptual preparation for second version. Initial work and integration with sentence splitter.

## version 2.0 2006-07

* New system architecture enabling swapping of alignment algorithm.
* Gale and Church re-implementation using the new architecture.
* Added support for multiple parsers and formatters. Added TXT parser and TMX formatter.
* Integration with segment SRX-rule based sentence splitter.

## version 2.1 2007-02

* Created a tool for calculating precision and recall of alignment to test its quality.
* TMX parser.

## version 2.2 2007-03

* Improved architecture based solely on filters (like UNIX programs).
* New text interface based on filters.

## version 2.3 2008-04

* Official name changed to mALIGNa. Put project on sourceforge.net (around January).
* Removed UNKNOWN_WORD and UNKNOWN_WID - instead of that UnifyAligner concept.
* Created maligna.sh script gathering all maligna commands.

## version 2.4 2008-08

* Created Oracle calculator - calculator that uses human-supplied alignment suggestions.
* Created meta-calculators - Minimum, Maximum and Composite.
* Cleaned up text interface.
* Improved, official support for Windows - fixed .bat scripts, fixed presentation formatter

## version 2.5 2009-04

* Segment SRX splitter library changed license to MIT and became part of mALIGNa distribution.
* Translated Readme and other documents to English based on Jimmy O'Regan work. JavaDocs are still Polish.
* Removed dependency on loomchild-util 

## version 2.6 2010-12

* English is the main maligna language - translated all JavaDocs and user documentation; improved documentation overall.
* API change: Moved Alignment and Category to coretypes package, changed Category role a little.
* Bug fixes for issues in ViterbiAlgorithm found by Anna Mündelein.
* Created IgnoreInfiniteProbabilityAlignmentsFilterDecorator. Now alignments with -INF score are preserved by all commands.

## version 2.7 2011-10

* Added progress meter to improve user interaction and as a basis for Okapi library integration.

## version 3.0 2015-09

* Rename root package from net.sourceforge.align to net.loomchild.maligna
* Migrate to Github
* Migrate to Maven build
* Publish maligna JAR on Maven Central

