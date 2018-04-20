package infrastructure;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.*;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.FileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by makri on 15/06/2017.
 */
public class KMobileElement extends MobileElement {
    public KMobileElement(MobileElement mobileElement) {
        this.mobileElement = mobileElement;
        this.id = mobileElement.getId();
       // this.fileDetector = mobileElement.file

    }

    public void setFileDetector(FileDetector fileDetector)
    {
        this.mobileElement.setFileDetector(fileDetector);
    }
    public String getId()
    {
       return this.mobileElement.getId();
    }

    public void setParent(RemoteWebDriver parent)
    {
        this.mobileElement.setParent(parent);
    }

    protected String id;
    protected RemoteWebDriver parent;
    protected FileDetector fileDetector;

    @Override
    public boolean isDisplayed()
    {
        return this.mobileElement.isDisplayed();
    }
    @Override
    public boolean isEnabled()
    {
        return this.mobileElement.isEnabled();
    }
    @Override
    public boolean isSelected()
    {
        return this.mobileElement.isSelected();
    }

    public KMobileElement() {
    }

    private MobileElement mobileElement;

    public MobileElement getMobileElement() {
        return this.mobileElement;
    }


    public Point getCenter() {
        return this.mobileElement.getCenter();
    }


    public List<KMobileElement> findKElements(By by) {
        try {
            this.mobileElement.isEnabled();
        } catch (org.openqa.selenium.StaleElementReferenceException staleElementReferenceException) {
            throw new IllegalArgumentException(this.mobileElement.toString() + " is staleElement");
        }


        List<MobileElement> mobileElements = this.mobileElement.findElements(by);
        List<KMobileElement> kMobileElements = new ArrayList<KMobileElement>();
        for (MobileElement mobileElement : mobileElements) {
            kMobileElements.add(new KMobileElement(mobileElement));
        }

        return kMobileElements;

    }

    public List<KMobileElement> findKElements(String by, String using) {
        try {
            this.mobileElement.isEnabled();
        } catch (org.openqa.selenium.StaleElementReferenceException staleElementReferenceException) {
            throw new IllegalArgumentException(this.mobileElement.toString() + " is staleElement");
        }


        List<MobileElement> mobileElements = this.mobileElement.findElements(by, using);
        List<KMobileElement> kMobileElements = new ArrayList<KMobileElement>();
        for (MobileElement mobileElement : mobileElements) {
            kMobileElements.add(new KMobileElement(mobileElement));
        }

        return kMobileElements;

    }

    public void click() {
        this.mobileElement.click();
    }

//    public List<MobileElement> findElementsById(String id) {
//        return super.findElementsById(id);
//    }
//
//    public List<MobileElement> findElementsByLinkText(String using) {
//        return super.findElementsByLinkText(using);
//    }
//
//    public List<MobileElement> findElementsByPartialLinkText(String using) {
//        return super.findElementsByPartialLinkText(using);
//    }
//
//    public List<MobileElement> findElementsByTagName(String using) {
//        return super.findElementsByTagName(using);
//    }
//
//    public List<MobileElement> findElementsByName(String using) {
//        return super.findElementsByName(using);
//    }
//
//    public List<MobileElement> findElementsByClassName(String using) {
//        return super.findElementsByClassName(using);
//    }
//
//    public List<MobileElement> findElementsByCssSelector(String using) {
//        return super.findElementsByCssSelector(using);
//    }
//
//    public List<MobileElement> findElementsByXPath(String using) {
//        return super.findElementsByXPath(using);
//    }

    public List<KMobileElement> findKElementsByAccessibilityId(String using) {

        try {
            this.mobileElement.isEnabled();
        } catch (org.openqa.selenium.StaleElementReferenceException staleElementReferenceException) {
            throw new IllegalArgumentException(this.mobileElement.toString() + " is staleElement");
        }


        List<MobileElement> mobileElements = this.mobileElement.findElementsByAccessibilityId(using);
        List<KMobileElement> kMobileElements = new ArrayList<KMobileElement>();
        for (MobileElement mobileElement : mobileElements) {
            kMobileElements.add(new KMobileElement(mobileElement));
        }

        return kMobileElements;
    }

    public void setValue(String value) {
        this.mobileElement.setValue(value);
    }

    public String getText() {
        return this.mobileElement.getText();
    }
}
