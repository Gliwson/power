package pl.power.domain.xmlDomain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class Capacity {

    @JacksonXmlProperty(localName = "availableCapacity")
    private BigDecimal availableCapacity;

    @JacksonXmlProperty(localName = "installedCapacity")
    private BigDecimal installedCapacity;

    @JacksonXmlProperty(localName = "unavailableCapacity")
    private BigDecimal unavailableCapacity;

    @JacksonXmlProperty(localName = "unitMeasure")
    private String unitMeasure;

}
