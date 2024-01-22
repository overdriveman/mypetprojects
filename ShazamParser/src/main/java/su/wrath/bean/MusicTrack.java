package su.wrath.bean;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;
import lombok.*;

import javax.xml.bind.annotation.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@XmlRootElement(name = "entry")
@XmlType(propOrder = {"artist", "title", "date"})
@XmlAccessorType(XmlAccessType.NONE)
@JsonPropertyOrder({"artist", "title", "date"})
public class MusicTrack {

    @JsonIgnore
    @XmlTransient
    @EqualsAndHashCode.Exclude
    @CsvBindByPosition(position = 0)
    private long indexNumber;

    @EqualsAndHashCode.Exclude
    @XmlElement(name = "date")
    @CsvBindByPosition(position = 1)
    @CsvDate(value = "uuuu-MM-dd")
    private LocalDate date;

    @XmlElement(name = "title")
    @CsvBindByPosition(position = 2)
    private String title;

    @XmlElement(name = "artist")
    @CsvBindByPosition(position = 3)
    private String artist;
}
