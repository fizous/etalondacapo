package etalon.purdue.edu.base.ui;

import android.text.Html;
import android.text.Spanned;
import android.widget.TextView;

import etalon.purdue.edu.base.BaseActivityHelper;

/**
 * Created by hussein on 5/31/16.
 */
public class BaseTextReport {
  /* static variable used to */
  private final static String DEFAULT_REPORT_AREA_NAME = "report_text_area";

  private final static String  LINE_SEP = System.getProperty("line.separator");


  private BaseActivityHelper mHelper;
  /**
   * The My view.
   */
/* instant variables */
  TextView myView;


  /**
   * Sets results title.
   *
   * @param title the title
   */
  public void setResultsTitle(String title) {
    Spanned formatted = Html.fromHtml("<b><i>" + title + ":</i></b><br/>");
    appendln(formatted);
  }

  /**
   * Instantiates a new Base text report.
   *
   * @param aHelper the a helper
   * @param name    the name
   */
  public BaseTextReport(BaseActivityHelper aHelper, String name) {
    this.mHelper = aHelper;
    this.myView = this.mHelper.getTextViewByName(name);
    setResultsTitle(this.mHelper.getPropValue("report_text_title"));
  }

  /**
   * Instantiates a new Base text report.
   *
   * @param aHelper the a helper
   */
  public BaseTextReport(BaseActivityHelper aHelper) {
    this(aHelper, DEFAULT_REPORT_AREA_NAME);
  }

  /**
   * Appendln.
   *
   * @param text the text
   */
  public void appendln(final String text) {
    mHelper.mActivity.runOnUiThread(new Runnable() {
      @Override
      public void run() {
        myView.append(text + LINE_SEP);
      }
    });

  }

  /**
   * Append errln.
   *
   * @param text the text
   */
  public void appendErrln(final String text) {
    mHelper.mActivity.runOnUiThread(new Runnable() {
      @Override
      public void run() {
        Spanned formatted = Html.fromHtml("<b><i>" + text + "</i></b><br/>");
        myView.append(formatted);
      }
    });

  }

  /**
   * Appendln.
   *
   * @param formatted the formatted
   */
  public void appendln(final Spanned formatted) {
    mHelper.mActivity.runOnUiThread(new Runnable() {
      @Override
      public void run() {
        myView.setText(formatted );
      }
    });
  }
}
