package net.sourceforge.align.formatter;

import static net.sourceforge.align.util.Util.merge;

import java.io.Writer;
import java.util.Date;
import java.util.List;

import net.sourceforge.align.coretypes.Alignment;
import net.sourceforge.align.util.bind.TmxMarshallerUnmarshaller;
import net.sourceforge.align.util.bind.tmx.Body;
import net.sourceforge.align.util.bind.tmx.Header;
import net.sourceforge.align.util.bind.tmx.Seg;
import net.sourceforge.align.util.bind.tmx.Tmx;
import net.sourceforge.align.util.bind.tmx.Tu;
import net.sourceforge.align.util.bind.tmx.Tuv;
import net.sourceforge.align.util.date.DateParser;


/**
 * Represents a formatter that produces TMX file output.
 * 
 * The output will be stored in {@link #TMX_VERSION} of TMX standard. 
 * Each alignment is represented by translation unit with properly set 
 * source and target languages. All source / target segments inside alignments 
 * are merged and space is inserted between them.
 * 
 * @see <a href="http://www.lisa.org/tmx/">TMX Standard</a>
 *
 * @author Jarek Lipski (loomchild)
 */
public class TmxFormatter implements Formatter {
	
	public static final String TMX_VERSION = "1.4b";

	public static final String TMX_ADMINLANG = "en";

	public static final String TMX_CREATIONTOOL = "mALIGNa";

	public static final String TMX_CREATIONTOOLVERSION = "2";

	public static final String TMX_SEGTYPE = "block";

	public static final String TMX_DATATYPE = "plaintext";
	
	public static final String TMX_OTMF = "al";

	private String sourceLanguageCode;

	private String targetLanguageCode;

	private Writer writer;
	
	public TmxFormatter(Writer writer, String sourceLanguageCode, 
			String targetLanguageCode) {
		this.writer = writer;
		this.sourceLanguageCode = sourceLanguageCode;
		this.targetLanguageCode = targetLanguageCode;
	}

	public void format(List<Alignment> alignmentList) {
		Tmx tmx = new Tmx();
		tmx.setVersion(TMX_VERSION);
		Header header = new Header();
		header.setAdminlang(TMX_ADMINLANG);
		header.setSrclang(sourceLanguageCode);
		header.setCreationtool(TMX_CREATIONTOOL);
		header.setCreationtoolversion(TMX_CREATIONTOOLVERSION);
		header.setSegtype(TMX_SEGTYPE);
		header.setDatatype(TMX_DATATYPE);
		header.setOTmf(TMX_OTMF);
		header.setCreationdate(DateParser.getIsoDateNoMillis(new Date()));
		tmx.setHeader(header);
		Body body = new Body();
		for (Alignment alignment : alignmentList) {
			Tu tu = new Tu();
			createTuv(tu, sourceLanguageCode, alignment.getSourceSegmentList());
			createTuv(tu, targetLanguageCode, alignment.getTargetSegmentList());
			if (tu.getTuv().size() > 0) {
				body.getTu().add(tu);
			}
		}
		tmx.setBody(body);
		TmxMarshallerUnmarshaller.getInstance().marshal(tmx, writer);
	}
	
	private void createTuv(Tu tu, String languageCode, 
			List<String> segmentList) {
		if (segmentList.size() > 0) {
			Tuv tuv = new Tuv();
			tuv.setLang(languageCode);
			Seg seg = new Seg();
			String segment = merge(segmentList);
			seg.getContent().add(segment);
			tuv.setSeg(seg);
			tu.getTuv().add(tuv);
		}
	}

}
