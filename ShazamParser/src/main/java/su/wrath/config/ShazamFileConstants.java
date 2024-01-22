package su.wrath.config;

import lombok.experimental.UtilityClass;

/**
 * Класс содержит заголовок и regexp формата библиотеки shazam
 *
 * <br/><br/>Пример ожидаемого формата shazam csv файла:
 * <pre>
 * Shazam Library
 * Index,TagTime,Title,Artist,URL,TrackKey
 * 1,2023-12-26,"Bathwater","No Doubt",https://www.shazam.com/track/289976/bathwater,289976
 * ...
 * </pre>
 */
@UtilityClass
public class ShazamFileConstants {

    public final String SHAZAM_HEADER = "Shazam Library";

    public final String SHAZAM_COLUMNS = "Index,TagTime,Title,Artist,URL,TrackKey";

    public final String SHAZAM_TRACK_REGEX = "\\d+,\\d{4}-\\d{2}-\\d{2},.+,.+,http.+,\\d+";
}
