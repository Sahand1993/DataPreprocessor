package com.braincourt.mysql.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.lang.reflect.Field;
import java.util.List;

import static com.braincourt.Constants.HAS_NUMBERS;

@Entity
public class RcvArticlesWithTopicTags extends RcvArticles {

    public int articleId;

    @Lob
    public String queryArticleIndices; // Can be either ngram och vocabulary indices

    public Long relevantId;

    public RcvArticlesWithTopicTags() {
    }

    public String c11;
    public String c12;
    public String c13;
    public String c14;
    public String c15;
    public String c16;
    public String c17;
    public String c18;
    public String c21;
    public String c22;
    public String c23;
    public String c24;
    public String c31;
    public String c32;
    public String c33;
    public String c34;
    public String c41;

    public String c42;
    public String e11;
    public String e12;
    public String e13;
    public String e14;
    public String e21;
    public String e31;
    public String e41;
    public String e51;
    public String e61;

    public String e71;

    public String g11;
    public String g12;
    public String g13;
    public String g14;
    public String g15;
    public boolean gcrim;
    public boolean gdef;
    public boolean gdip;
    public boolean gdis;
    public boolean gedu;
    public boolean gent;
    public boolean genv;
    public boolean gfas;
    public boolean ghea;
    public boolean gjob;
    public boolean gmil;
    public boolean gobit;
    public boolean godd;
    public boolean gpol;
    public boolean gpro;
    public boolean grel;
    public boolean gsci;
    public boolean gspo;
    public boolean gtour;
    public boolean gvio;
    public boolean gvote;
    public boolean gwea;
    public boolean gwelf;

    public String m11;
    public String m12;
    public String m13;
    public String m14;
    public boolean meur;

    public RcvArticlesWithTopicTags(int articleId,
                                    String articleIndices) {
        this.articleId = articleId;
        this.queryArticleIndices = articleIndices;
    }

    public void setRelevantId(Long relevantId) {
        this.relevantId = relevantId;
    }

    public String getC11() {
        return c11;
    }

    public void setC11(String c11) {
        this.c11 = c11;
    }

    public String getC12() {
        return c12;
    }

    public void setC12(String c12) {
        this.c12 = c12;
    }

    public String getC13() {
        return c13;
    }

    public void setC13(String c13) {
        this.c13 = c13;
    }

    public String getC14() {
        return c14;
    }

    public void setC14(String c14) {
        this.c14 = c14;
    }

    public String getC15() {
        return c15;
    }

    public void setC15(String c15) {
        this.c15 = c15;
    }

    public String getC16() {
        return c16;
    }

    public void setC16(String c16) {
        this.c16 = c16;
    }

    public String getC17() {
        return c17;
    }

    public void setC17(String c17) {
        this.c17 = c17;
    }

    public String getC18() {
        return c18;
    }

    public void setC18(String c18) {
        this.c18 = c18;
    }

    public String getC21() {
        return c21;
    }

    public void setC21(String c21) {
        this.c21 = c21;
    }

    public String getC22() {
        return c22;
    }

    public void setC22(String c22) {
        this.c22 = c22;
    }

    public String getC23() {
        return c23;
    }

    public void setC23(String c23) {
        this.c23 = c23;
    }

    public String getC24() {
        return c24;
    }

    public void setC24(String c24) {
        this.c24 = c24;
    }

    public String getC31() {
        return c31;
    }

    public void setC31(String c31) {
        this.c31 = c31;
    }

    public String getC32() {
        return c32;
    }

    public void setC32(String c32) {
        this.c32 = c32;
    }

    public String getC33() {
        return c33;
    }

    public void setC33(String c33) {
        this.c33 = c33;
    }

    public String getC34() {
        return c34;
    }

    public void setC34(String c34) {
        this.c34 = c34;
    }

    public String getC41() {
        return c41;
    }

    public void setC41(String c41) {
        this.c41 = c41;
    }

    public String getC42() {
        return c42;
    }

    public void setC42(String c42) {
        this.c42 = c42;
    }

    public String getE11() {
        return e11;
    }

    public void setE11(String e11) {
        this.e11 = e11;
    }

    public String getE12() {
        return e12;
    }

    public void setE12(String e12) {
        this.e12 = e12;
    }

    public String getE13() {
        return e13;
    }

    public void setE13(String e13) {
        this.e13 = e13;
    }

    public String getE14() {
        return e14;
    }

    public void setE14(String e14) {
        this.e14 = e14;
    }

    public String getE21() {
        return e21;
    }

    public void setE21(String e21) {
        this.e21 = e21;
    }

    public String getE31() {
        return e31;
    }

    public void setE31(String e31) {
        this.e31 = e31;
    }

    public String getE41() {
        return e41;
    }

    public void setE41(String e41) {
        this.e41 = e41;
    }

    public String getE51() {
        return e51;
    }

    public void setE51(String e51) {
        this.e51 = e51;
    }

    public String getE61() {
        return e61;
    }

    public void setE61(String e61) {
        this.e61 = e61;
    }

    public String getE71() {
        return e71;
    }

    public void setE71(String e71) {
        this.e71 = e71;
    }

    public String getG11() {
        return g11;
    }

    public void setG11(String g11) {
        g11 = g11;
    }

    public String getG12() {
        return g12;
    }

    public void setG12(String g12) {
        g12 = g12;
    }

    public String getG13() {
        return g13;
    }

    public void setG13(String g13) {
        g13 = g13;
    }

    public String getG14() {
        return g14;
    }

    public void setG14(String g14) {
        g14 = g14;
    }

    public String getG15() {
        return g15;
    }

    public void setG15(String g15) {
        g15 = g15;
    }

    public boolean isGCRIM() {
        return gcrim;
    }

    public void setGCRIM(boolean GCRIM) {
        this.gcrim = GCRIM;
    }

    public boolean isGDEF() {
        return gdef;
    }

    public void setGDEF(boolean GDEF) {
        this.gdef = GDEF;
    }

    public boolean isGDIP() {
        return gdip;
    }

    public void setGDIP(boolean GDIP) {
        this.gdip = GDIP;
    }

    public boolean isGDIS() {
        return gdis;
    }

    public void setGDIS(boolean GDIS) {
        this.gdis = GDIS;
    }

    public boolean isGEDU() {
        return gedu;
    }

    public void setGEDU(boolean GEDU) {
        this.gedu = GEDU;
    }

    public boolean isGENT() {
        return gent;
    }

    public void setGENT(boolean GENT) {
        this.gent = GENT;
    }

    public boolean isGENV() {
        return genv;
    }

    public void setGENV(boolean GENV) {
        this.genv = GENV;
    }

    public boolean isGFAS() {
        return gfas;
    }

    public void setGFAS(boolean GFAS) {
        this.gfas = GFAS;
    }

    public boolean isGHEA() {
        return ghea;
    }

    public void setGHEA(boolean GHEA) {
        this.ghea = GHEA;
    }

    public boolean isGJOB() {
        return gjob;
    }

    public void setGJOB(boolean GJOB) {
        this.gjob = GJOB;
    }

    public boolean isGMIL() {
        return gmil;
    }

    public void setGMIL(boolean GMIL) {
        this.gmil = GMIL;
    }

    public boolean isGOBIT() {
        return gobit;
    }

    public void setGOBIT(boolean GOBIT) {
        this.gobit = GOBIT;
    }

    public boolean isGODD() {
        return godd;
    }

    public void setGODD(boolean GODD) {
        this.godd = GODD;
    }

    public boolean isGPOL() {
        return gpol;
    }

    public void setGPOL(boolean GPOL) {
        this.gpol = GPOL;
    }

    public boolean isGPRO() {
        return gpro;
    }

    public void setGPRO(boolean GPRO) {
        this.gpro = GPRO;
    }

    public boolean isGREL() {
        return grel;
    }

    public void setGREL(boolean GREL) {
        this.grel = GREL;
    }

    public boolean isGSCI() {
        return gsci;
    }

    public void setGSCI(boolean GSCI) {
        this.gsci = GSCI;
    }

    public boolean isGSPO() {
        return gspo;
    }

    public void setGSPO(boolean GSPO) {
        this.gspo = GSPO;
    }

    public boolean isGTOUR() {
        return gtour;
    }

    public void setGTOUR(boolean GTOUR) {
        this.gtour = GTOUR;
    }

    public boolean isGVIO() {
        return gvio;
    }

    public void setGVIO(boolean GVIO) {
        this.gvio = GVIO;
    }

    public boolean isGVOTE() {
        return gvote;
    }

    public void setGVOTE(boolean GVOTE) {
        this.gvote = GVOTE;
    }

    public boolean isGWEA() {
        return gwea;
    }

    public void setGWEA(boolean GWEA) {
        this.gwea = GWEA;
    }

    public boolean isGWELF() {
        return gwelf;
    }

    public void setGWELF(boolean GWELF) {
        this.gwelf = GWELF;
    }

    public String getM11() {
        return m11;
    }

    public void setM11(String m11) {
        m11 = m11;
    }

    public String getM12() {
        return m12;
    }

    public void setM12(String m12) {
        m12 = m12;
    }

    public String getM13() {
        return m13;
    }

    public void setM13(String m13) {
        m13 = m13;
    }

    public String getM14() {
        return m14;
    }

    public void setM14(String m14) {
        m14 = m14;
    }

    public boolean isMEUR() {
        return meur;
    }

    public void setMEUR(boolean MEUR) {
        this.meur = MEUR;
    }

    boolean hasTag(List<String> tags, String tag) {
        return tags.stream().anyMatch(currentTag -> currentTag.equals(tag));
    }

    public void setAllTags(List<String> tags) {
        try {
            for (String tag : tags) {
                if (tag.endsWith("CAT")) {
                    continue;
                }
                tag = tag.toLowerCase();
                if (HAS_NUMBERS.matcher(tag).matches()) {
                    Field tagField;
                    if (tag.length() < 4) {
                        tagField = getClass().getField(tag);

                    } else {
                        tagField = getClass().getField(tag.substring(0, 3));

                    }
                    String newTagValue = (tagField.get(this) != null ? tagField.get(this).toString() + "," : "") + tag;
                    tagField.set(this, newTagValue);
                } else {
                    Field tagField = getClass().getField(tag);
                    tagField.set(this, true);
                }

            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
