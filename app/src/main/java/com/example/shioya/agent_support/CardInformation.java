package com.example.shioya.agent_support;

/**
 * Created by shioya on 2017/03/10.
 */

//public enum CardInformation {
//}

// ALL は条件絞り込み検索で「すべて」を選んだ場合に使うやつ
enum AgentAttribute {
    SECRET, PUBLIC, NORMAL, ALL
}


enum Gender {
    MALE, FEMALE, NONE, ALL
}

enum AgentPackage {
    DEFAULT, AUGMENT, ALL
}

enum AgentName {
    // エージェント一覧
    SPEAKER(AgentAttribute.SECRET, Gender.MALE, AgentPackage.DEFAULT),
    TANK(AgentAttribute.SECRET, Gender.MALE,AgentPackage.DEFAULT),
    OBLIQUESHADOW(AgentAttribute.SECRET, Gender.FEMALE, AgentPackage.DEFAULT),
    TITANIUM(AgentAttribute.SECRET, Gender.MALE, AgentPackage.DEFAULT),
    LARKLADY(AgentAttribute.SECRET, Gender.FEMALE, AgentPackage.DEFAULT),
    SWANMAIDEN(AgentAttribute.SECRET, Gender.FEMALE, AgentPackage.DEFAULT),
    HUNTRESS(AgentAttribute.SECRET, Gender.FEMALE, AgentPackage.DEFAULT),
    BLACKHAND(AgentAttribute.SECRET, Gender.MALE, AgentPackage.DEFAULT),
    NIGHTMARE(AgentAttribute.SECRET, Gender.FEMALE, AgentPackage.DEFAULT),
    BARONBRUMAIRE(AgentAttribute.SECRET, Gender.MALE, AgentPackage.DEFAULT),
    MARKJUNIOR(AgentAttribute.NORMAL, Gender.MALE, AgentPackage.DEFAULT),
    STATICELECTRICITY(AgentAttribute.NORMAL, Gender.FEMALE, AgentPackage.DEFAULT),
    VIPER(AgentAttribute.NORMAL, Gender.MALE, AgentPackage.DEFAULT),
    ANGEL(AgentAttribute.NORMAL, Gender.FEMALE, AgentPackage.DEFAULT),
    SAVAGEASSASSIN(AgentAttribute.NORMAL, Gender.MALE, AgentPackage.DEFAULT),
    DOUBLEKNIGHT(AgentAttribute.NORMAL, Gender.MALE, AgentPackage.DEFAULT),
    KWONKIKU(AgentAttribute.NORMAL, Gender.MALE, AgentPackage.DEFAULT),
    DARKFLOW(AgentAttribute.NORMAL, Gender.MALE, AgentPackage.DEFAULT),
    DIAMONDMAN(AgentAttribute.NORMAL, Gender.MALE, AgentPackage.DEFAULT),
    PERFUME(AgentAttribute.NORMAL, Gender.FEMALE, AgentPackage.DEFAULT),
    TOUGHGUN(AgentAttribute.NORMAL, Gender.MALE, AgentPackage.DEFAULT),
    REDBLADE(AgentAttribute.NORMAL, Gender.MALE, AgentPackage.DEFAULT),
    ALIAS(AgentAttribute.NORMAL, Gender.FEMALE, AgentPackage.DEFAULT),
    BACKFIRE(AgentAttribute.NORMAL, Gender.MALE, AgentPackage.DEFAULT),
    TRINITY(AgentAttribute.NORMAL, Gender.FEMALE, AgentPackage.DEFAULT),
    J(AgentAttribute.NORMAL, Gender.MALE, AgentPackage.DEFAULT),
    DETECTIVE(AgentAttribute.NORMAL, Gender.MALE, AgentPackage.DEFAULT),

    // 拡張
    CHAINER(AgentAttribute.SECRET, Gender.MALE, AgentPackage.AUGMENT),
    PSYCHOPATH(AgentAttribute.NORMAL, Gender.MALE, AgentPackage.AUGMENT),
    GOLDHEAD(AgentAttribute.SECRET, Gender.MALE, AgentPackage.AUGMENT),
    NEUTRAL(AgentAttribute.SECRET, Gender.MALE, AgentPackage.AUGMENT),
    GAMEMASTER(AgentAttribute.NORMAL, Gender.NONE, AgentPackage.AUGMENT),
    DUELIST(AgentAttribute.SECRET, Gender.FEMALE, AgentPackage.AUGMENT),
    HANDSTANDER(AgentAttribute.SECRET, Gender.MALE, AgentPackage.AUGMENT),
    SMALLSPACE(AgentAttribute.SECRET, Gender.FEMALE, AgentPackage.AUGMENT),
    FILLER(AgentAttribute.NORMAL, Gender.MALE, AgentPackage.AUGMENT),
    MOMENTSLEEP(AgentAttribute.NORMAL, Gender.MALE, AgentPackage.AUGMENT),
    VITALITYRECORDER(AgentAttribute.NORMAL, Gender.MALE, AgentPackage.AUGMENT),
    CHAIRMAN(AgentAttribute.SECRET, Gender.MALE, AgentPackage.AUGMENT),
    AMNESIA(AgentAttribute.NORMAL, Gender.MALE, AgentPackage.AUGMENT),
    TIPSY(AgentAttribute.NORMAL, Gender.MALE, AgentPackage.AUGMENT),
    PEPPER(AgentAttribute.NORMAL, Gender.NONE, AgentPackage.AUGMENT),
    JUGGLER(AgentAttribute.PUBLIC, Gender.MALE, AgentPackage.AUGMENT),
    KUROSAWA(AgentAttribute.PUBLIC, Gender.MALE, AgentPackage.AUGMENT),
    LIFEGAMER(AgentAttribute.SECRET, Gender.MALE, AgentPackage.AUGMENT),
    SUNFLOWER(AgentAttribute.SECRET, Gender.MALE, AgentPackage.AUGMENT),
    FISHCAKE(AgentAttribute.SECRET, Gender.NONE, AgentPackage.AUGMENT),
    HOST(AgentAttribute.SECRET, Gender.MALE, AgentPackage.AUGMENT),
    MORPHEUS(AgentAttribute.NORMAL, Gender.MALE, AgentPackage.AUGMENT),
    TRUMPETER(AgentAttribute.SECRET, Gender.MALE, AgentPackage.AUGMENT),
    KEYBOARDER(AgentAttribute.SECRET, Gender.FEMALE, AgentPackage.AUGMENT),
    JULIUS(AgentAttribute.SECRET, Gender.MALE, AgentPackage.AUGMENT),
    FONTJUNKIE(AgentAttribute.NORMAL, Gender.MALE, AgentPackage.AUGMENT),
    SPRINCAR(AgentAttribute.PUBLIC, Gender.MALE, AgentPackage.AUGMENT),
    BLACKPEPPER(AgentAttribute.NORMAL, Gender.NONE, AgentPackage.AUGMENT),
    SHELLKEAPER(AgentAttribute.NORMAL, Gender.FEMALE, AgentPackage.AUGMENT),
    IPHONE(AgentAttribute.SECRET, Gender.MALE, AgentPackage.AUGMENT),

    // 初期化用
    ALLFILTER(AgentAttribute.ALL, Gender.ALL, AgentPackage.ALL);


    public final AgentAttribute agentAttribute;
    public final Gender gender;
    public final AgentPackage agentPackage;

    // コンストラクタ
    private AgentName(AgentAttribute agentAttribute, Gender gender, AgentPackage agentPackage){
        this.agentAttribute = agentAttribute;
        this.gender = gender;
        this.agentPackage = agentPackage;
    }

    // 小文字の名前を得る resにアクセスするのに必要
    public String getName() {
        return name().toLowerCase();
    }




}
