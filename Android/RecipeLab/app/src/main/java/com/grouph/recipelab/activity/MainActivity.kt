//
//  작성자 : 정민재
//

package com.grouph.recipelab.activity

import android.content.Intent
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
import com.google.android.material.navigation.NavigationView
import com.grouph.recipelab.R
import com.grouph.recipelab.adapter.ResearchingListAdapter
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.toolbar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    View.OnClickListener {

    private val TAG = "MainActivity"

    var testData1: ArrayList<String> = arrayListOf("연구중인음식1", "진행중2", "테스트용데이터3")
    var testData2: ArrayList<String> = arrayListOf(
        "완료된 레시피1", "완료된 레시피2", "완료됨3", "스크롤테스트1", "스크롤테스트2")

    lateinit var rvTop: RecyclerView
    lateinit var rvBottom: RecyclerView
    lateinit var adapterTop: ResearchingListAdapter
    lateinit var adapterBottom: ResearchingListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val myToolbar: Toolbar = toolbar
        // 메인액티비티에서 네비게이션 드로워를 사용하기 위해 테마를 NoActionBar로 바꾸고 이를 대체한 커스텀 툴바 설정
        // 타이틀을 설정해주기 위해 임시로 작성한 코드
        myToolbar.title = "MainActivity"
        setSupportActionBar(myToolbar)

        // 좌측에서 끌어서 사용하는 네비게이션 뷰를 초기화하는 부분
        val navView: NavigationView = findViewById(R.id.nav_view)
        // 네비게이션 메뉴의 아이템을 클릭했을때 반응하기 위한 이벤트 리스너 설정
        navView.setNavigationItemSelectedListener(this@MainActivity)

        // 리사이클러뷰에 데이터를 바인드해주기 위해 필요한 어댑터 생성
        adapterTop = ResearchingListAdapter(testData1, this, R.layout.item_research_list_card)
        adapterTop.notifyDataSetChanged()
        adapterBottom = ResearchingListAdapter(testData2, this, R.layout.item_research_list)
        adapterBottom.notifyDataSetChanged()

        // 리사이클러뷰에 커스텀 어댑터를 설정하고, 좌우로 움직이도록 설정, 레이아웃간 구분선 추가
        rvTop = rv_list_researching
        rvTop.apply {
            adapter = adapterTop
            layoutManager = LinearLayoutManager(context ,LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(DividerItemDecoration(context , DividerItemDecoration.HORIZONTAL))
        }
        // 상단의 카드 레이아웃이 화면 경계에 딱 맞춰지도록 하는 라이브러리 추가
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(rvTop)

        rvBottom = rv_list_research_finished
        rvBottom.apply {
            adapter = adapterBottom
            layoutManager = LinearLayoutManager(context ,LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(context , DividerItemDecoration.VERTICAL))
            isNestedScrollingEnabled = false
        }
    }

    override fun onClick(v: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    // 네비게이션 드로워를 생성하는 메서드
    override fun onSupportNavigateUp(): Boolean {
        return true
    }

    // 네비게이션 드로워의 아이템 클릭을 감지하는 이벤트 리스너
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
