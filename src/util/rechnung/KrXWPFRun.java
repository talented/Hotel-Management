package util.rechnung;

import org.apache.poi.xwpf.usermodel.IRunBody;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFonts;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;

/**
 * XWPFRun object defines a region of text with a common set of properties
 * @author Alexander
 *
 */

public class KrXWPFRun extends XWPFRun {

	public KrXWPFRun(XWPFRun run) {
		this(run.getCTR(), run.getParent());
	}

	public KrXWPFRun(CTR r, IRunBody p) {
		super(r, p);
	}

	@Override
	public void setFontFamily(String fontFamily) {
		CTR run = getCTR();
		CTRPr pr = run.isSetRPr() ? run.getRPr() : run.addNewRPr();
		CTFonts fonts = pr.isSetRFonts() ? pr.getRFonts() : pr.addNewRFonts();
		fonts.setHAnsi(fontFamily);
		fonts.setEastAsia(fontFamily);
		fonts.setAscii(fontFamily);
		
		
		super.setFontFamily(fontFamily);

	}
	
	

}