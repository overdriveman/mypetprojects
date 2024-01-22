package su.wrath.formatter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.CommandLine;
import su.wrath.bean.MusicTrack;
import su.wrath.bean.XmlTracksContainer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.List;

@Slf4j
public class XmlFormatter implements Formatter {

    public void format(List<MusicTrack> tracks, CommandLine cmd) {
        try {
            JAXBContext context = JAXBContext.newInstance(XmlTracksContainer.class);
            Marshaller mar = context.createMarshaller();
            mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            XmlTracksContainer xmlTracks = new XmlTracksContainer();
            xmlTracks.setTracks(tracks);

            mar.marshal(xmlTracks, new File(cmd.getOptionValue("xml")));
        } catch (JAXBException e) {
            log.warn("Ошибка в XmlFormatter: ", e);
        }
    }
}
