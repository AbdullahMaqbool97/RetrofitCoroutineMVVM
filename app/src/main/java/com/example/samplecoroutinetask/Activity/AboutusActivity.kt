package com.example.samplecoroutinetask.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.samplecoroutinetask.R
import mehdi.sakout.aboutpage.AboutPage
import mehdi.sakout.aboutpage.Element
import android.widget.Toast

import android.view.Gravity
import android.view.View
import java.lang.String
import java.util.*

class AboutusActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val adsElement = Element()
        adsElement.setTitle("Advertise with us")

        val aboutPage = AboutPage(this)
            .isRTL(false)
            .setImage(R.drawable.cakepop)
            .addItem(Element().setTitle("Version 6.2"))
            .addItem(adsElement)
            .addGroup("Connect with us")
            .addEmail("elmehdi.sakout@gmail.com")
            .addWebsite("https://mehdisakout.com/")
            .addFacebook("the.medy")
            .addTwitter("medyo80")
            .addYoutube("UCdPQtdWIsg7_pi4mrRu46vA")
            .addPlayStore("com.ideashower.readitlater.pro")
            .addInstagram("medyo80")
            .addGitHub("medyo")
            .addItem(getCopyRightsElement())
            .create()
        setContentView(aboutPage)
    }

    fun getCopyRightsElement(): Element {
        val copyRightsElement = Element()
        val copyrights =
            String.format(getString(R.string.copy_right), Calendar.getInstance().get(Calendar.YEAR))
        copyRightsElement.setTitle(copyrights)
        copyRightsElement.setIconDrawable(R.drawable.about_icon_copy_right)
        copyRightsElement.autoApplyIconTint = true
        copyRightsElement.setIconTint(mehdi.sakout.aboutpage.R.color.about_item_icon_color)
        copyRightsElement.iconNightTint = android.R.color.white
        copyRightsElement.gravity = Gravity.CENTER
        copyRightsElement.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                Toast.makeText(this@AboutusActivity, copyrights, Toast.LENGTH_SHORT).show()
            }
        })
        return copyRightsElement
    }
}