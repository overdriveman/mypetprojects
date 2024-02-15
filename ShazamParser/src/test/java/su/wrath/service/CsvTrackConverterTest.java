package su.wrath.service;

import org.apache.commons.cli.*;
import org.junit.jupiter.api.Test;
import su.wrath.bean.MusicTrack;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;
import static su.wrath.TestHelpers.*;

public class CsvTrackConverterTest {
    CsvTrackConverter converter = new CsvTrackConverter();

    CommandLine cmdMock = mock(CommandLine.class);

    MusicTrack fridayTrack = new MusicTrack(1, LocalDate.of(2024, 1, 12), "Title", "Artist");
    MusicTrack mondayTrack = new MusicTrack(1, LocalDate.of(2024, 1, 8), "Title", "Artist");

    MusicTrack februaryTrack = new MusicTrack(1, LocalDate.of(2024, 2, 29), "Title", "Artist");
    MusicTrack marchTrack = new MusicTrack(1, LocalDate.of(2024, 3, 2), "Title", "Artist");

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

    @Test
    public void getFilterWithoutOption() {
        reset(cmdMock);
        when(cmdMock.hasOption("filter-date-after")).thenReturn(false);

        Predicate<MusicTrack> predicate = converter.getFilter(cmdMock);

        assertNotNull(predicate);
        verify(cmdMock, times(1)).hasOption("filter-date-after");
        verify(cmdMock, never()).getOptionValue("filter-date-after");
        assertTrue(predicate.test(fridayTrack));
        assertTrue(predicate.test(mondayTrack));
    }

    @Test
    public void getFilterWithFaultyDate() {
        reset(cmdMock);
        when(cmdMock.hasOption("filter-date-after")).thenReturn(true);
        when(cmdMock.getOptionValue("filter-date-after")).thenReturn("22.13.2024");

        Predicate<MusicTrack> predicate = converter.getFilter(cmdMock);

        assertNotNull(predicate);
        verify(cmdMock, times(1)).hasOption("filter-date-after");
        verify(cmdMock, times(1)).getOptionValue("filter-date-after");
        assertFalse(predicate.test(fridayTrack), "getFilter with incorrect date is always false predicate");
        assertFalse(predicate.test(mondayTrack), "getFilter with incorrect date is always false predicate");
    }

    @Test
    public void getFilterWithNormalDateAndDotAsSeparator() {
        reset(cmdMock);
        when(cmdMock.hasOption("filter-date-after")).thenReturn(true);
        when(cmdMock.getOptionValue("filter-date-after")).thenReturn("10.01.2024");

        Predicate<MusicTrack> predicate = converter.getFilter(cmdMock);

        assertNotNull(predicate);
        verify(cmdMock, times(1)).hasOption("filter-date-after");
        verify(cmdMock, times(1)).getOptionValue("filter-date-after");
        assertTrue(predicate.test(fridayTrack));
        assertFalse(predicate.test(mondayTrack));
    }

    @Test
    public void getFilterWithNormalDateAndSlashAsSeparator() {
        reset(cmdMock);
        when(cmdMock.hasOption("filter-date-after")).thenReturn(true);
        when(cmdMock.getOptionValue("filter-date-after")).thenReturn("2024/10/01");

        Predicate<MusicTrack> predicate = converter.getFilter(cmdMock);

        assertNotNull(predicate);
        verify(cmdMock, times(1)).hasOption("filter-date-after");
        verify(cmdMock, times(1)).getOptionValue("filter-date-after");
        assertTrue(predicate.test(fridayTrack));
        assertFalse(predicate.test(mondayTrack));
    }

    @Test
    public void getFilterWithNormalDateAndDashAsSeparator() {
        reset(cmdMock);
        when(cmdMock.hasOption("filter-date-after")).thenReturn(true);
        when(cmdMock.getOptionValue("filter-date-after")).thenReturn("10-01-2024");

        Predicate<MusicTrack> predicate = converter.getFilter(cmdMock);

        assertNotNull(predicate);
        verify(cmdMock, times(1)).hasOption("filter-date-after");
        verify(cmdMock, times(1)).getOptionValue("filter-date-after");
        assertTrue(predicate.test(fridayTrack));
        assertFalse(predicate.test(mondayTrack));
    }

    @Test
    public void getFilterWithNormalDateAndTrackBeforeThat() {
        reset(cmdMock);
        when(cmdMock.hasOption("filter-date-after")).thenReturn(true);
        when(cmdMock.getOptionValue("filter-date-after")).thenReturn("10-01-2024");

        Predicate<MusicTrack> predicate = converter.getFilter(cmdMock);
        MusicTrack lateTrack = new MusicTrack(1, LocalDate.of(2024, 1, 9), "Title", "Artist");

        assertNotNull(predicate);
        verify(cmdMock, times(1)).hasOption("filter-date-after");
        verify(cmdMock, times(1)).getOptionValue("filter-date-after");
        assertFalse(predicate.test(lateTrack));
        assertFalse(predicate.test(mondayTrack));
    }

    @Test
    public void getFilterWith29thFebruaryOf2023rd() {
        reset(cmdMock);
        when(cmdMock.hasOption("filter-date-after")).thenReturn(true);
        when(cmdMock.getOptionValue("filter-date-after")).thenReturn("29-02-2023");

        Predicate<MusicTrack> predicate = converter.getFilter(cmdMock);

        MusicTrack february2023Track = new MusicTrack(1, LocalDate.of(2023, 2, 28), "title", "artist");

        assertNotNull(predicate);
        assertFalse(predicate.test(february2023Track), "Filter should return always false predicate on faulty date");
        assertFalse(predicate.test(februaryTrack), "February 29 of 2024 is invalid and false");
        assertFalse(predicate.test(marchTrack), "March track should not pass the filter");
    }

    @Test
    public void getFilterWith29thFebruaryOf2024rd() {
        reset(cmdMock);
        when(cmdMock.hasOption("filter-date-after")).thenReturn(true);
        when(cmdMock.getOptionValue("filter-date-after")).thenReturn("29-02-2024");

        Predicate<MusicTrack> predicate = converter.getFilter(cmdMock);

        assertNotNull(predicate);
        assertTrue(predicate.test(marchTrack), "Track with march date should pass");
        assertFalse(predicate.test(februaryTrack), "Track with february date should be filtered");

    }

    @Test
    public void getFilterWith30thFebruary() {
        reset(cmdMock);
        when(cmdMock.hasOption("filter-date-after")).thenReturn(true);
        when(cmdMock.getOptionValue("filter-date-after")).thenReturn("30-02-2024");

        Predicate<MusicTrack> predicate = converter.getFilter(cmdMock);

        assertNotNull(predicate);
        assertFalse(predicate.test(februaryTrack), "Filter should return always false predicate on faulty date");
        assertFalse(predicate.test(marchTrack), "March track should not pass the filter");
    }

    @Test
    public void getFilterWith31thFebruary() {
        reset(cmdMock);
        when(cmdMock.hasOption("filter-date-after")).thenReturn(true);
        when(cmdMock.getOptionValue("filter-date-after")).thenReturn("31-02-2024");

        Predicate<MusicTrack> predicate = converter.getFilter(cmdMock);

        assertNotNull(predicate);
        assertFalse(predicate.test(februaryTrack), "Filter should return always false predicate on faulty date");
        assertFalse(predicate.test(marchTrack), "Filter should return always false predicate on faulty date");
    }
}
