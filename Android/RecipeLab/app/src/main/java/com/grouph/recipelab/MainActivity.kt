//
//  작성자 : 정민재
//

package com.grouph.recipelab

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.toolbar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    View.OnClickListener {

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
                intent = Intent(this,ResearchResultActivity::class.java)
            }
            R.id.nav_add_recipe -> {
                intent = Intent(this,AddRecipeActivity::class.java)
            }
            R.id.nav_add_research -> {
                intent = Intent(this,AddResearchActivity::class.java)
            }
            R.id.nav_researching_list -> {
                intent = Intent(this,ResearchingListActivity::class.java)
            }
        }

        if (intent != null) {
            startActivity(intent)
        }
        return true
    }
}
