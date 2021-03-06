//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.3 in JDK 1.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.01.12 at 06:38:36 ���� CST 
//


package org.neolm.batools.core.job.config;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="JobName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="JobTrigger" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="IndexFileFilter">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="IncludeExtension" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="Value" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="TargetPaths">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="TargetPath" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="EntrySvnFileName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="JobParameters">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Param" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="ParamName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="ParamValue" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "jobName",
    "jobTrigger",
    "indexFileFilter",
    "targetPaths",
    "entrySvnFileName",
    "jobParameters"
})
@XmlRootElement(name = "JobForRepositoryConfig")
public class JobForRepositoryConfig {

    @XmlElement(name = "JobName", required = true)
    protected String jobName;
    @XmlElement(name = "JobTrigger", required = true)
    protected String jobTrigger;
    @XmlElement(name = "IndexFileFilter", required = true)
    protected JobForRepositoryConfig.IndexFileFilter indexFileFilter;
    @XmlElement(name = "TargetPaths", required = true)
    protected JobForRepositoryConfig.TargetPaths targetPaths;
    @XmlElement(name = "EntrySvnFileName", required = true)
    protected String entrySvnFileName;
    @XmlElement(name = "JobParameters", required = true)
    protected JobForRepositoryConfig.JobParameters jobParameters;

    /**
     * Gets the value of the jobName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJobName() {
        return jobName;
    }

    /**
     * Sets the value of the jobName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJobName(String value) {
        this.jobName = value;
    }

    /**
     * Gets the value of the jobTrigger property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJobTrigger() {
        return jobTrigger;
    }

    /**
     * Sets the value of the jobTrigger property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJobTrigger(String value) {
        this.jobTrigger = value;
    }

    /**
     * Gets the value of the indexFileFilter property.
     * 
     * @return
     *     possible object is
     *     {@link JobForRepositoryConfig.IndexFileFilter }
     *     
     */
    public JobForRepositoryConfig.IndexFileFilter getIndexFileFilter() {
        return indexFileFilter;
    }

    /**
     * Sets the value of the indexFileFilter property.
     * 
     * @param value
     *     allowed object is
     *     {@link JobForRepositoryConfig.IndexFileFilter }
     *     
     */
    public void setIndexFileFilter(JobForRepositoryConfig.IndexFileFilter value) {
        this.indexFileFilter = value;
    }

    /**
     * Gets the value of the targetPaths property.
     * 
     * @return
     *     possible object is
     *     {@link JobForRepositoryConfig.TargetPaths }
     *     
     */
    public JobForRepositoryConfig.TargetPaths getTargetPaths() {
        return targetPaths;
    }

    /**
     * Sets the value of the targetPaths property.
     * 
     * @param value
     *     allowed object is
     *     {@link JobForRepositoryConfig.TargetPaths }
     *     
     */
    public void setTargetPaths(JobForRepositoryConfig.TargetPaths value) {
        this.targetPaths = value;
    }

    /**
     * Gets the value of the entrySvnFileName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEntrySvnFileName() {
        return entrySvnFileName;
    }

    /**
     * Sets the value of the entrySvnFileName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEntrySvnFileName(String value) {
        this.entrySvnFileName = value;
    }

    /**
     * Gets the value of the jobParameters property.
     * 
     * @return
     *     possible object is
     *     {@link JobForRepositoryConfig.JobParameters }
     *     
     */
    public JobForRepositoryConfig.JobParameters getJobParameters() {
        return jobParameters;
    }

    /**
     * Sets the value of the jobParameters property.
     * 
     * @param value
     *     allowed object is
     *     {@link JobForRepositoryConfig.JobParameters }
     *     
     */
    public void setJobParameters(JobForRepositoryConfig.JobParameters value) {
        this.jobParameters = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="IncludeExtension" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="Value" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "includeExtension"
    })
    public static class IndexFileFilter {

        @XmlElement(name = "IncludeExtension")
        protected JobForRepositoryConfig.IndexFileFilter.IncludeExtension includeExtension;

        /**
         * Gets the value of the includeExtension property.
         * 
         * @return
         *     possible object is
         *     {@link JobForRepositoryConfig.IndexFileFilter.IncludeExtension }
         *     
         */
        public JobForRepositoryConfig.IndexFileFilter.IncludeExtension getIncludeExtension() {
            return includeExtension;
        }

        /**
         * Sets the value of the includeExtension property.
         * 
         * @param value
         *     allowed object is
         *     {@link JobForRepositoryConfig.IndexFileFilter.IncludeExtension }
         *     
         */
        public void setIncludeExtension(JobForRepositoryConfig.IndexFileFilter.IncludeExtension value) {
            this.includeExtension = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="Value" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "value"
        })
        public static class IncludeExtension {

            @XmlElement(name = "Value")
            protected List<String> value;

            /**
             * Gets the value of the value property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the value property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getValue().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link String }
             * 
             * 
             */
            public List<String> getValue() {
                if (value == null) {
                    value = new ArrayList<String>();
                }
                return this.value;
            }

        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="Param" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="ParamName" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="ParamValue" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "param"
    })
    public static class JobParameters {

        @XmlElement(name = "Param")
        protected List<JobForRepositoryConfig.JobParameters.Param> param;

        /**
         * Gets the value of the param property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the param property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getParam().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link JobForRepositoryConfig.JobParameters.Param }
         * 
         * 
         */
        public List<JobForRepositoryConfig.JobParameters.Param> getParam() {
            if (param == null) {
                param = new ArrayList<JobForRepositoryConfig.JobParameters.Param>();
            }
            return this.param;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="ParamName" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="ParamValue" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "paramName",
            "paramValue"
        })
        public static class Param {

            @XmlElement(name = "ParamName", required = true)
            protected String paramName;
            @XmlElement(name = "ParamValue", required = true)
            protected String paramValue;

            /**
             * Gets the value of the paramName property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getParamName() {
                return paramName;
            }

            /**
             * Sets the value of the paramName property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setParamName(String value) {
                this.paramName = value;
            }

            /**
             * Gets the value of the paramValue property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getParamValue() {
                return paramValue;
            }

            /**
             * Sets the value of the paramValue property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setParamValue(String value) {
                this.paramValue = value;
            }

        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="TargetPath" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "targetPath"
    })
    public static class TargetPaths {

        @XmlElement(name = "TargetPath")
        protected List<String> targetPath;

        /**
         * Gets the value of the targetPath property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the targetPath property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getTargetPath().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getTargetPath() {
            if (targetPath == null) {
                targetPath = new ArrayList<String>();
            }
            return this.targetPath;
        }

    }

}
