package ru.tayrinn.hustle.radiohustle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;

import java.lang.reflect.Type;
import java.util.List;

import ru.tayrinn.hustle.radiohustle.model.Track;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Type listType = new TypeToken<List<Track>>() {}.getType();

        List<Track> tracks = gson.fromJson("[\n " +
        "\t{\"name\":\"audio/Adele_-_108_bpm_Set_Fire_to_the_Rain.mp3\",\"bpm\":\"108\"},\n" +
                "\t{\"name\":\"audio/Akon_Feat_Kardinal_Offishall_-_Dangerous.mp3\",\"bpm\":\"117\"}]", listType);
    }
}