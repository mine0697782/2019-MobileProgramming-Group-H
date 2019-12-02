/**
 *  작성자 : 정민재
 *  @author jmj
 */

package com.grouph.recipelab.activity

import android.content.Intent
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.grouph.recipelab.R
import com.grouph.recipelab.adapter.RecipeListAdapter
import com.grouph.recipelab.helper.MySQLIteOpenHelper
import com.grouph.recipelab.model.Recipe
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.toolbar_main.*
import me.relex.circleindicator.CircleIndicator2

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    View.OnClickListener {

    private val TAG = "MainActivity"

    private lateinit var helper: MySQLIteOpenHelper
    private lateinit var db: SQLiteDatabase
    val dbName = "file.db"
    var dbVersion = 1

    val dataTop = arrayListOf<Recipe>()
    val dataBottom = arrayListOf<Recipe>()

    lateinit var rvTop: RecyclerView
    lateinit var rvBottom: RecyclerView
    lateinit var adapterTop: RecipeListAdapter
    lateinit var adapterBottom: RecipeListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val myToolbar: Toolbar = toolbar

        /** 메인액티비티에서 네비게이션 드로워를 사용하기 위해 테마를 NoActionBar로 바꾸고 이를 대체한 커스텀 툴바 설정 */
        /** 타이틀을 설정해주기 위해 임시로 작성한 코드 */
        myToolbar.title = "RecipeLab"
        setSupportActionBar(myToolbar)

        /** 좌측에서 끌어서 사용하는 네비게이션 뷰를 초기화하는 부분 */
        val navView: NavigationView = findViewById(R.id.nav_view)

        /** 네비게이션 메뉴의 아이템을 클릭했을때 반응하기 위한 이벤트 리스너 설정 */
        navView.setNavigationItemSelectedListener(this@MainActivity)

        /** 데이터베이스를 사용하기 위한 헬퍼 */
        helper = MySQLIteOpenHelper(this, dbName,null, dbVersion)
        getDataFromDB()



        val floatingBtn: FloatingActionButton = fab
        floatingBtn.setOnClickListener {
//            adapterTop.data.add(Recipe("추가된 데이터",0, "1","2","3"))
//            adapterTop.notifyDataSetChanged()
            startActivityForResult(Intent(this, AddRecipeActivity::class.java), 0)
        }

        /** 리사이클러뷰에 데이터를 바인드해주기 위해 필요한 어댑터 생성 */
        adapterTop = RecipeListAdapter(dataTop, this, R.layout.item_research_list_card)
        adapterTop.notifyDataSetChanged()
        adapterBottom = RecipeListAdapter(dataBottom, this, R.layout.item_research_list)
        adapterBottom.notifyDataSetChanged()

        /** 리사이클러뷰에 커스텀 어댑터를 설정하고, 좌우로 움직이도록 설정 */
        rvTop = rv_list_researching
        rvTop.apply {
            adapter = adapterTop
            layoutManager = LinearLayoutManager(context ,LinearLayoutManager.HORIZONTAL, false)
        }
        /** 상단의 카드 레이아웃이 화면 경계에 딱 맞춰지도록 하는 라이브러리 추가 */
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(rvTop)

        /** 한 아이템이 페이지를 가득 채우는 리사이클러뷰에 아이템의 갯수와 현재 아이템의 인덱스를 표시해주는 라이브러리 */
        val indicator: CircleIndicator2 = indicator_main
        indicator.attachToRecyclerView(rvTop, snapHelper)
        /** 데이터의 변화를 감지해서 점의 갯수를 실시간으로 바꿔줌 */
        adapterTop.registerAdapterDataObserver(indicator.getAdapterDataObserver())

        /** 상하로 움직이는 리사이클러뷰. */
        rvBottom = rv_list_research_finished
        rvBottom.apply {
            adapter = adapterBottom
            layoutManager = LinearLayoutManager(context ,LinearLayoutManager.VERTICAL, false)
            /** 아이템간 구분선을 만들어주는 함수 */
            addItemDecoration(DividerItemDecoration(context , DividerItemDecoration.VERTICAL))
            /** 상위 레이아웃의 스크롤과 중첩되는걸 방지하기 위해 스크롤 비활성화 */
            isNestedScrollingEnabled = false
        }
    }

    fun getDataFromDB() {
        try {
            db = helper.readableDatabase
            var cursor = db.rawQuery("select * from recipeTable where isFInished = 0", null)
            while (cursor.moveToNext()) {
                cursor.apply {
                    val data = Recipe(getString(3), getInt(1),
                        getString(6), getString(7), getString(8),
                        getInt(2), recipeNo = getInt(0))
                    dataTop.add(data)
                }
            }
            cursor = db.rawQuery("select * from recipeTable where isFInished = 1", null)
            while (cursor.moveToNext()) {
                cursor.apply {
                    val data = Recipe(getString(3), getInt(1),
                        getString(6), getString(7), getString(8),
                        getInt(2), recipeNo = getInt(0))
                    dataBottom.add(data)
                }
            }
            db.close()
        } catch (e: SQLiteException) {
            e.printStackTrace()
        }
    }

    fun refreshAdapter() {
//        Toast.makeText(this, "refresh", Toast.LENGTH_SHORT).show()
        adapterTop.data.clear()
        adapterBottom.data.clear()
        getDataFromDB()
        adapterTop.notifyDataSetChanged()
        adapterBottom.notifyDataSetChanged()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            Toast.makeText(this,"result Ok", Toast.LENGTH_SHORT).show()
            refreshAdapter()
        }
    }

    override fun onClick(v: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /** 네비게이션 드로워를 생성하는 메서드 */
    override fun onSupportNavigateUp(): Boolean {
        return true
    }

    /** 네비게이션 드로워의 아이템 클릭을 감지하는 이벤트 리스너 */
    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        Toast.makeText(this,p0.toString(),Toast.LENGTH_SHORT).show()
        var intent: Intent? = null

        when(p0.itemId) {
            R.id.nav_main -> {

            }
            R.id.nav_research_result_list -> {
                intent = Intent(this, ResearchResultActivity::class.java)
            }
            R.id.nav_add_recipe -> {
                intent = Intent(this, AddRecipeActivity::class.java)
            }
            R.id.nav_add_research -> {
                intent = Intent(this, AddResearchActivity::class.java)
            }
            R.id.nav_researching_list -> {
                intent = Intent(this, ResearchingListActivity::class.java)
            }
        }

        if (intent != null) {
            startActivity(intent)
        }
        return true
    }
}
