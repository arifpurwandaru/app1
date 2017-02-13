package un.app1.splash;

import com.stephentuso.welcome.BasicPage;
import com.stephentuso.welcome.ParallaxPage;
import com.stephentuso.welcome.WelcomeActivity;
import com.stephentuso.welcome.WelcomeConfiguration;

import un.app1.R;

public class AppWelcome extends WelcomeActivity {

    @Override
    protected WelcomeConfiguration configuration() {
        return new WelcomeConfiguration.Builder(this)
                .defaultTitleTypefacePath("Montserrat-Bold.ttf")
                .defaultHeaderTypefacePath("Montserrat-Bold.ttf")
                .page(new BasicPage(R.drawable.ic_front_desk_white,
                        getResources().getString(R.string.strTitlePageOne),
                        getResources().getString(R.string.strDescriptionPageOne))
                        .background(R.color.orange_background)
                ).page(new BasicPage(R.drawable.ic_thumb_up_white,
                        getResources().getString(R.string.strTitlePageTwo),
                        getResources().getString(R.string.strDescriptionPageTwo))
                        .background(R.color.red_background)
                ).page(new ParallaxPage(R.layout.parallax_example,
                        getResources().getString(R.string.strTitlePageThree),
                        getResources().getString(R.string.strDescriptionPageThree))
                        .lastParallaxFactor(2f)
                        .background(R.color.purple_background)
                ).swipeToDismiss(false)
                .canSkip(false)
                .exitAnimation(android.R.anim.fade_out)
                .build();
    }

}
