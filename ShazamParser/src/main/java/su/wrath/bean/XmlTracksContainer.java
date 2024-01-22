package su.wrath.bean;

import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Класс нужен для маппера xml
 */
@XmlRootElement(name = "Library")
@NoArgsConstructor
public class XmlTracksContainer {

    @XmlElement(name = "entry")
    private List<MusicTrack> tracks;

    public void setTracks(List<MusicTrack> tracks) {
        this.tracks = tracks;
    }

}
