package pucsim4.caterpillar

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.games.Games
import com.google.android.gms.games.LeaderboardsClient

class leaderboard: AppCompatActivity() {
    private var googleSignInClient: GoogleSignInClient? = null
    private var leaderboardsClient: LeaderboardsClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initGoogleClientAndSignin()

        leaderboardsClient?.submitScore("id", 100)

    }

    fun initGoogleClientAndSignin() {
        googleSignInClient = GoogleSignIn.getClient(this, GoogleSignInOptions.Builder(
            GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN).build())

        googleSignInClient?.silentSignIn()?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                leaderboardsClient = Games.getLeaderboardsClient(this, task.result!!)
            } else {
                Log.e("Error", "signInError", task.exception)
            }
        }
    }

    fun showTopPlayers(view: View) {
        leaderboardsClient?.allLeaderboardsIntent?.addOnSuccessListener {intent ->
            startActivityForResult(intent, 0)
        }
    }
}