package su.wrath.service;

import org.apache.commons.cli.*;
import org.junit.jupiter.api.Test;
import su.wrath.bean.MusicTrack;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;
import static su.wrath.TestHelpers.*;

public class CsvTrackConverterTest {
    CsvTrackConverter converter = new CsvTrackConverter();

    CommandLine cmdMock = mock(CommandLine.class);

    @Test
    public void checkComparatorByArtist() {
        reset(cmdMock);

        when(cmdMock.hasOption("sort-by-artist")).thenReturn(true);
        Comparator<MusicTrack> comparator = converter.getComparator(cmdMock);

        assertNotNull(comparator);

        verify(cmdMock, atMostOnce()).hasOption("sort-by-artist");
        verify(cmdMock, never()).hasOption("sort-by-title");
        verify(cmdMock, never()).hasOption("sort-by-date");

        verify(cmdMock, atMostOnce()).hasOption(anyString());
    }

    @Test
    public void checkComparatorByTitle() {
        reset(cmdMock);

        when(cmdMock.hasOption("sort-by-artist")).thenReturn(false);
        when(cmdMock.hasOption("sort-by-title")).thenReturn(true);

        Comparator<MusicTrack> comparator = converter.getComparator(cmdMock);

        assertNotNull(comparator);

        verify(cmdMock, atMostOnce()).hasOption("sort-by-artist");
        verify(cmdMock, atMostOnce()).hasOption("sort-by-title");
        verify(cmdMock, never()).hasOption("sort-by-date");

        verify(cmdMock, times(2)).hasOption(anyString());
    }

    @Test
    public void checkComparatorByDate() {
        reset(cmdMock);

        when(cmdMock.hasOption("sort-by-artist")).thenReturn(false);
        when(cmdMock.hasOption("sort-by-title")).thenReturn(false);
        when(cmdMock.hasOption("sort-by-date")).thenReturn(true);

        Comparator<MusicTrack> comparator = converter.getComparator(cmdMock);

        assertNotNull(comparator);

        verify(cmdMock, atMostOnce()).hasOption("sort-by-artist");
        verify(cmdMock, atMostOnce()).hasOption("sort-by-title");
        verify(cmdMock, atMostOnce()).hasOption("sort-by-date");

        verify(cmdMock, times(3)).hasOption(anyString());
    }

    @Test
    public void checkComparatorByIndexNumber() {
        reset(cmdMock);

        when(cmdMock.hasOption("sort-by-artist")).thenReturn(false);
        when(cmdMock.hasOption("sort-by-title")).thenReturn(false);
        when(cmdMock.hasOption("sort-by-date")).thenReturn(false);

        Comparator<MusicTrack> comparator = converter.getComparator(cmdMock);

        assertNotNull(comparator);

        verify(cmdMock, atMostOnce()).hasOption("sort-by-artist");
        verify(cmdMock, atMostOnce()).hasOption("sort-by-title");
        verify(cmdMock, atMostOnce()).hasOption("sort-by-date");

        verify(cmdMock, times(3)).hasOption(anyString());
    }

    @Test
    public void checkCsvTrackConverterRemovesDuplicates() {
        CommandLine cmd = prepareCommandLine(getPathToTestFile("ShazTestInput.csv").toString());
        List<MusicTrack> tracks = converter.parseFile(cmd);

        MusicTrack first = tracks.get(0), last = tracks.get(tracks.size() - 1);

        assertEquals(19, tracks.size(), "Должно быть 19 уникальных треков в тестовом файле");

        assertAll("Без сортировки первый трек",
                () -> assertTrackAuthorEquals("Train", first),
                () -> assertTrackTitleEquals("Drive By", first),
                () -> assertTrackDateEquals("2023-05-25", first)
        );
        assertAll("Без сортировки последний трек",
                () -> assertTrackAuthorEquals("Felix", last),
                () -> assertTrackTitleEquals("Que Mas", last),
                () -> assertTrackDateEquals("2023-05-18", last)
        );
    }

    @Test
    public void checkCsvTrackConverterSortsByAuthor() {
        CommandLine cmd = prepareCommandLine(getPathToTestFile("ShazTestInput.csv").toString(), "-a");
        List<MusicTrack> tracks = converter.parseFile(cmd);
        MusicTrack first = tracks.get(0), last = tracks.get(tracks.size() - 1);

        assertAll("Сортировка по автору первый трек",
                () -> assertTrackAuthorEquals("48th St. Collective", first),
                () -> assertTrackTitleEquals("Say Say Say (feat. Eve St. Jones)", first),
                () -> assertTrackDateEquals("2023-05-23", first)
        );
        assertAll("Сортировка по автору последний трек",
                () -> assertTrackAuthorEquals("Train", last),
                () -> assertTrackTitleEquals("Drive By", last),
                () -> assertTrackDateEquals("2023-05-25", last)
        );
    }

    @Test
    public void checkCsvTrackConverterSortsByTrackName() {
        CommandLine cmd = prepareCommandLine(getPathToTestFile("ShazTestInput.csv").toString(), "-n");
        List<MusicTrack> tracks = converter.parseFile(cmd);
        MusicTrack first = tracks.get(0), last = tracks.get(tracks.size() - 1);

        assertAll("Сортировка по треку первый трек",
                () -> assertTrackAuthorEquals("Lizette & Nova Bossa Ltd.", first),
                () -> assertTrackTitleEquals("99 Red Balloons", first),
                () -> assertTrackDateEquals("2023-05-19", first)
        );
        assertAll("Сортировка по треку последний трек",
                () -> assertTrackAuthorEquals("Chris Cornell", last),
                () -> assertTrackTitleEquals("You Know My Name", last),
                () -> assertTrackDateEquals("2023-05-20", last)
        );
    }

    @Test
    public void checkCsvTrackConverterSortsByAdditionDate() {
        CommandLine cmd = prepareCommandLine(getPathToTestFile("ShazTestInput.csv").toString(), "-s");
        List<MusicTrack> tracks = converter.parseFile(cmd);
        MusicTrack first = tracks.get(0), last = tracks.get(tracks.size() - 1);

        assertAll("Сортировка по дате первый трек",
                () -> assertTrackAuthorEquals("Pinto Picasso", first),
                () -> assertTrackTitleEquals("Nada Es Igual", first),
                () -> assertTrackDateEquals("2023-05-18", first)
        );
        assertAll("Сортировка по дате последний трек",
                () -> assertTrackAuthorEquals("Train", last),
                () -> assertTrackTitleEquals("Drive By", last),
                () -> assertTrackDateEquals("2023-05-25", last)
        );
    }

    @Test
    public void parseInputFileReturnsEmptyStream() {
        CommandLine cmd = prepareCommandLine(getPathToTestFile("ShazTestInput.csv").toString());

        assertTrue(converter.parseInputFile("NonExistingFile", cmd).isEmpty(), "Метод parseInputFile возвращает пустой список из пустого файла");
    }
}
