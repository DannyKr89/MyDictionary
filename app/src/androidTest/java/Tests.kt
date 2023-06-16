import com.example.api.retrofit.SearchListApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.dk.mydictionary.data.SearchListRepoImpl
import ru.dk.mydictionary.data.model.Word
import tech.thdev.network.flowcalladapterfactory.FlowCallAdapterFactory

class Tests {

    private val api = Retrofit.Builder().baseUrl("https://dictionary.skyeng.ru/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(FlowCallAdapterFactory()).build().create(SearchListApi::class.java)
    private val searchListRepoImpl = SearchListRepoImpl(api)

    @Test
    fun test_searchRepo() {
        CoroutineScope(Dispatchers.IO).launch {
            val list = mutableListOf<Word>()
            searchListRepoImpl.getDataAsync("word").collect() {
                list.addAll(it)
            }
            assertTrue(list.isNotEmpty())
        }
    }

    @Test
    fun test_forEquals() {
        CoroutineScope(Dispatchers.IO).launch {
            val list = mutableListOf<Word>()
            searchListRepoImpl.getDataAsync("word").collect() {
                list.addAll(it)
            }
            assertEquals("word", list.first().word)
        }
    }

    @Test
    fun test_forNotNull() {
        CoroutineScope(Dispatchers.IO).launch {
            val list = mutableListOf<Word>()
            searchListRepoImpl.getDataAsync("").collect() {
                list.addAll(it)
            }
            assertNotNull(list)
        }
    }
}