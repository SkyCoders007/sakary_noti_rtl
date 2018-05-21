
package com.mxi.android.salarynotification.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.mxi.android.salarynotification.R;

public class AboutUsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        WebView webView = (WebView) findViewById(R.id.wv_about_us);
        // webView.loadUrl("file:///android_asset/about_us.html");

        webView.getSettings();
        webView.setBackgroundColor(Color.TRANSPARENT);
        final String mimeType = "text/html";
        String html = " <p style=\"text-align:right;font-size:15px;color:#ffffff;\">" +
                "<br/> نظام رواتبي للهواتف الذكية" +
                "        من انتاج شركة الحلول الهندسية والتي تأسست عام 2005.<br/>" +
                "        استشاريون متخصصون في مجال الدفع الالكتروني ، تقنية المعلومات والاتصالات والانظمة المتكاملة.<br/>" +
                "        انظمة ادارة الافراد وحسب شروط قانون العمل العراقي.<br/>" +
                "        انظمة ادارة الرواتب.<br/>" +
                "        نظام ادارة الحسابات المتكامل وحسب شروط النظام المحاسبي العراقي.<br/>" +
                "        نظام ادارة المخازن.<br/>" +
                "        نظام ادارة الموجودات.<br/>" +
                "        نظام ادارة المشتريات والمبيعات.<br/>" +
                "        ادارة العقودة وارشفتها الذاتية.<br/>" +
                "        نظام ادارة المستشفيات المتكامل.<br/>" +
                "        واي انظمة اخرى وحسب الطلب<br/>" +
                "      زورونا على موقع الشركة : <a style=\"color:#ffffff;\" href=\"http://www.escoiq.com\" target=\"_blank\"> www.escoiq.com</a>\n" +
                "<br/>        او اتصلو بنا على الهاتف :<a style=\"color:#ffffff;\" href=\"tel:009647706448102\">07706448102</a>\n" +
                "    </p>";
        final String encoding = "UTF-8";


        webView.loadDataWithBaseURL("", html, mimeType, encoding, "");

    }

}

