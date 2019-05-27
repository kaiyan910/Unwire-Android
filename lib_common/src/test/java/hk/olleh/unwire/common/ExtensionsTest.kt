package hk.olleh.unwire.common

import android.util.Log
import net.lachlanmckee.timberjunit.TimberTestRule
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import timber.log.Timber

@RunWith(MockitoJUnitRunner.Strict::class)
class ExtensionsTest {

    @Rule
    @JvmField
    var timberRule = TimberTestRule.builder()
        .minPriority(Log.DEBUG)
        .showThread(true)
        .showTimestamp(false)
        .onlyLogWhenTestFails(false)
        .build()

    @Test
    fun testFindFirstMatchPattern() {
        val pattern = """<img[^>]+src="(.*?)"[^>]*/>"""
        val content = """
            <p>蘋果今日正式宣佈一年一度的全球開發者大會 WWDC 2019 的舉行時間，將會在 2019 年 6 月 3 日到 6 月 7 日於加利福尼亞州的聖荷西舉行。
            而在香港時間 6 月 4 日凌晨 1 時正，會有一場 Keynote 發佈會，屆時可以在美國的 Apple 官網觀看直播。</p>\n
            <p><img class="alignnone size-medium wp-image-810796" src="https://cdn.unwire.hk/wp-content/uploads/2019/05/WWDC2019-1-694x384.png" alt="" width="694" height="384" srcset="https://cdn.unwire.hk/wp-content/uploads/2019/05/WWDC2019-1-694x384.png 694w, https://cdn.unwire.hk/wp-content/uploads/2019/05/WWDC2019-1-768x425.png 768w, https://cdn.unwire.hk/wp-content/uploads/2019/05/WWDC2019-1-1280x709.png 1280w, https://cdn.unwire.hk/wp-content/uploads/2019/05/WWDC2019-1-1200x665.png 1200w, https://cdn.unwire.hk/wp-content/uploads/2019/05/WWDC2019-1.png 1600w" sizes="(max-width: 694px) 100vw, 694px" /></p>\n
            <p>蘋果公司每年都會在 WWDC 大會中，發佈最新一代作業系統，新系統包括 iOS、macOS、watchOS及 tvOS，亦會有少數機會，會在發佈會中發佈新硬件產品，當中主要是 Mac 電腦系列的更新。</p>
        """.trimIndent()

        val result = content.findFirstMatchPattern(pattern)

        Timber.d("[DEBUG] result=$result")

        assertTrue(result == "https://cdn.unwire.hk/wp-content/uploads/2019/05/WWDC2019-1-694x384.png")
    }
}