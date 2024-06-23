package thePackmaster.cards.marisapack;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.StarBounceEffect;
import thePackmaster.powers.shamanpack.IgnitePower;
import thePackmaster.util.Wiz;
import thePackmaster.vfx.marisapack.CasualFlameParticleEffect;
import thePackmaster.vfx.marisapack.FireIgniteEffect;

import static thePackmaster.SpireAnniversary5Mod.makeID;

public class LuminousStrike extends AbstractMarisaCard implements AmplifyCard {
    public final static String ID = makeID(LuminousStrike.class.getSimpleName());
    private static final int DMG = 8, UPG_DMG = 3;

    public LuminousStrike() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        damage = baseDamage = DMG;
        tags.add(CardTags.STRIKE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        for (int i = 0; i < damage / 2; i++) {
            Wiz.vfx(new StarBounceEffect(m.hb.cX, m.hb.cY));
        }
    }

    public void upp() {
        upgradeDamage(UPG_DMG);
    }

    @Override
    public boolean skipUseOnAmplify() {
        return false;
    }

    @Override
    public void useAmplified(AbstractPlayer p, AbstractMonster m) {
        Wiz.vfx(new FireIgniteEffect(m.hb.cX, m.hb.cY, damage));
        for (int i = 0; i < 4; i++) {
            Wiz.vfx(new CasualFlameParticleEffect(m.hb.cX + (MathUtils.random(-10, 10) * Settings.scale), m.hb.cY + (MathUtils.random(-10, 10) * Settings.scale)));
        }
        if (damage > 1) { // 1 damage would apply 0
            Wiz.applyToEnemy(m, new IgnitePower(m, damage / 2));
        }
    }

    @Override
    public int getAmplifyCost() {
        return 1;
    }
}
