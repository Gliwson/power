package pl.power.domain.xmlDomain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JacksonXmlRootElement(namespace = "http://www.acer.europa.eu/REMIT/REMITUMMCommonSchema_V1.xsd", localName = "REMITUrgentMarketMessages")
public class RemitUMM {

    private String xmlns;

    @JacksonXmlProperty(
            isAttribute = true
            , namespace = "http://www.acer.europa.eu/REMIT/REMITUMMCommonSchema_V1.xsd",
            localName = "ns2")
    private String ns2;


    @JacksonXmlProperty(localName = "UMM")
    private Umm umm;

}
