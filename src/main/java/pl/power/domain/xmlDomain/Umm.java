package pl.power.domain.xmlDomain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class Umm {

    @JacksonXmlProperty(localName = "biddingZone")
    private String biddingZone;

    @JacksonXmlProperty(localName = "unavailabilityReason")
    private String unavailabilityReason;

    @JacksonXmlProperty(localName = "fuelType")
    private String fuelType;

    @JacksonXmlProperty(localName = "publicationDateTime")
    private Timestamp publicationDateTime;

    @JacksonXmlProperty(localName = "affectedAsset")
    private NameAndEic affectedAsset;

    @JacksonXmlProperty(localName = "messageId")
    private String messageId;

    @JacksonXmlProperty(localName = "event")
    private Event event;

    @JacksonXmlProperty(localName = "marketParticipant")
    private NameAndEic marketParticipant;

    @JacksonXmlProperty(localName = "unavailabilityType")
    private String unavailabilityType;

    @JacksonXmlProperty(localName = "capacity")
    private Capacity capacity;

}
