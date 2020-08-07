package com.example.buyit.shezhi.tuozhanjiemian;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.buyit.R;

public class userAgreement extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_agreement);

        ImageView back_image_button = findViewById(R.id.back_image_button);
        back_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView scrollview_txt = findViewById(R.id.scrollview_txt);
        scrollview_txt.setText(
                          "        在此特别提醒各位用户请认真阅读、充分理解本《软件许可及安装协议》（下称《协议》)。\n" +
                          "\n        用户应认真阅读充分理解本《协议》中的各条款。您的安装、使用和登录buyit团队app等行为将视为" +
                          "对本《协议》的接受，并同意接受本《协议》各项条款的约束。\n" +
                          "        本《协议》是您（用户）与本团队之间关于用户安装、使用软件，注册、使用、管理软件；以及使用本团队提供的" +
                          "相关服务所订立的协议。\n" +
                          "\n        本《协议》描述本团队与用户之间关于软件许可使用及服务相关方面的权利义务，而用户是指通过" +
                          "本团队提供的软件授权和buyit团队注册的途径，获得软件产品及号码授权许可，以及使用本团队提供的相关服务的个人或组织。\n" +
                          "\n        本《协议》可由本团队随时线上更新，更新后的协议条款一旦公布即代替原来的协议条款，恕不再另行通知。" +
                          "用户可重新下载安装本软件查阅最新版协议条款。在本团队修改《协议》条款后，如果用户不接受修改后的条款，" +
                          "请立即停止使用本团队提供的软件和服务，用户继续使用本团队提供的软件和服务将被视为已接受了修改后的协议。\n" +
                          "\n        除本《协议》有明确规定外，本《协议》并未" +
                          "对利用本“软件”使用的公司或合作单位的其他服务规定相关的服务条款。对于这些服务，一般有单独的服务条款加以规范，用户" +
                          "须在使用有关服务时另行了解与确认。\n" +
                          "\n        单独的服务条款与本协议有冲突的地方，以单独的服务条款为准。如用户使用该服务，视为对相关服务条款的接受。\n"
                         );

        TextView statement_of_use = findViewById(R.id.statement_of_use);
        statement_of_use.setText(
                        "1、知识产权声明\n" +
                        "        本APP是由福建农林大学金山学院2017级计算机科学与技术专业2人开发“软件”的一切版权、商标权、专利权、" +
                        "商业秘密等知识产权，以及与本APP相关的所有信息内容，包括但不限于：文字表述及其组合、图标、图饰、图表、色彩、界面" +
                        "设计、版面框架、有关数据、印刷材料、或电子文档等均受中华人民共和国著作权法、商标法、专利法、反不正当竞争法和" +
                        "相应的国际条约以及其他知识产权法律法规的保护，除涉及第三方授权的软件或技术外，本团队享有上述知识产权。\n" +
                        "\n2、软件授权范围\n" +
                        "\n2.1  用户可以在个人计算机或者手机上安装、使用、显示、运行本软件。\n" +
                        "\n2.2  保留权利：未明示授权的其他一切权利仍归本团队所有，用户使用其他权利时须另外取得本团队的书面同意。\n" +
                        "\n2.3  除本《协议》有明确规定外，本《协议》并未对利用本软件访问的公司或合作单位的其他服务规定相关的服务条款，对于这些服务有可能有单独的服务条款加以规范，请用户在使用有关服务时另行了解与确认。\n" +
                        "\n3、关于软件使用\n" +
                        "\n3.1  用户可以通过在线申请方式加入buyit团队，成为buyit团队的会员。会员可享受优惠价格购买产品和免费门店体验服务。会员实行积分制度，每次消费满人民币一百元及以上即可享受一次积分，不满一百元的部分不计入积分。积分可以多次消费累积，上不封顶。积分可以在各大门店换取免费美容保养项目，积分当年有效，次年清零。会员实行一人卡号制度，会员服务仅限本人使用，不得转借他人。\n" +
                        "\n3.2  用户可以申请成为buyit团队的代理人，享受会员待遇。代理人须遵守本协议，实行自愿原则。\n\n" +
                        "\n4、利用的许可\n" +
                        "\n4.1  用户违反本《协议》或相关的服务条款的规定，导致或产生的任何第三方主张的任何索赔、要求或损失，包括合理的律师费" +
                        "，用户需同意赔偿。\n" +
                        "\n4.2  使用本APP由用户自己承担风险，本团队及合作单位对本“软件\"不作任何类型的担保，不论是明示的、默示的或法令的保证" +
                        "和条件，包括但不限于本APP的适销性、适用性、无病毒、无疏忽或无技术瑕疵问题、所有权和无侵权的明示或默示担保" +
                        "和条件，对在任何情况下因使用或不能使用本APP所产生的直接、间接、偶然、特殊及后续的损害及风险，本团队及合作单位不承担任何责任。\n" +
                        "\n4.3  使用本APP需要涉及到互联网服务，可能会受到各个环节不稳定因素的影响，存在因不可抗力、计算机病毒、黑客攻击、" +
                         "系统不稳定、非法内容信息、骚扰信息屏蔽以及其他任何网络、技术、通信线路、信息安全管理措施等原因造成的用户" +
                         "的经济损失，本团队及合作单位不承担任何责任。\n" +
                        "\n4.4  因技术故障等不可抗事件影响到服务的正常运行的，本团队及合作单位承诺在第一时间内与相关单位配合，及时处理进行" +
                        "修复，但用户因此而遭受的一切损失，本团队及合作单位不承担责任。\n\n" +
                        "5、其他条款\n" +
                        "\n5.1  本《协议》的解释、效力及纠纷的解决，适用于中华人民共和国法律。若用户和本团队之间发生任何纠纷或争议，" +
                        "首先应友好协商解决，协商不成的，用户在此完全同意将纠纷或争议提交本团队所在地即福建有管辖权的人民法院管辖。\n" +
                        "\n5.2  本《协议》版权由本团队所有，本团队保留一切解释权利。本文中提及的软件和服务名称为本团队的注册商标或商标，" +
                        "受法律保护。\n"
        );

        exit.getInstance().addActivity(this);
    }
}