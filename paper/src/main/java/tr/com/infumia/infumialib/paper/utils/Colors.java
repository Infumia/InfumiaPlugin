package tr.com.infumia.infumialib.paper.utils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public final class Colors {

  /**
   * The color alice blue with an RGB value of #F0F8FF
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#F0F8FF;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors ALICEBLUE = new Colors(0.9411765f, 0.972549f, 1.0f);

  /**
   * The color antique white with an RGB value of #FAEBD7
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#FAEBD7;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors ANTIQUEWHITE = new Colors(0.98039216f, 0.92156863f, 0.84313726f);

  /**
   * The color aqua with an RGB value of #00FFFF
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#00FFFF;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors AQUA = new Colors(0.0f, 1.0f, 1.0f);

  /**
   * The color aquamarine with an RGB value of #7FFFD4
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#7FFFD4;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors AQUAMARINE = new Colors(0.49803922f, 1.0f, 0.83137256f);

  /**
   * The color azure with an RGB value of #F0FFFF
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#F0FFFF;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors AZURE = new Colors(0.9411765f, 1.0f, 1.0f);

  /**
   * The color beige with an RGB value of #F5F5DC
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#F5F5DC;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors BEIGE = new Colors(0.9607843f, 0.9607843f, 0.8627451f);

  /**
   * The color bisque with an RGB value of #FFE4C4
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#FFE4C4;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors BISQUE = new Colors(1.0f, 0.89411765f, 0.76862746f);

  /**
   * The color black with an RGB value of #000000
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#000000;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors BLACK = new Colors(0.0f, 0.0f, 0.0f);

  /**
   * The color blanched almond with an RGB value of #FFEBCD
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#FFEBCD;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors BLANCHEDALMOND = new Colors(1.0f, 0.92156863f, 0.8039216f);

  /**
   * The color blue with an RGB value of #0000FF
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#0000FF;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors BLUE = new Colors(0.0f, 0.0f, 1.0f);

  /**
   * The color blue violet with an RGB value of #8A2BE2
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#8A2BE2;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors BLUEVIOLET = new Colors(0.5411765f, 0.16862746f, 0.8862745f);

  /**
   * The color brown with an RGB value of #A52A2A
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#A52A2A;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors BROWN = new Colors(0.64705884f, 0.16470589f, 0.16470589f);

  /**
   * The color burly wood with an RGB value of #DEB887
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#DEB887;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors BURLYWOOD = new Colors(0.87058824f, 0.72156864f, 0.5294118f);

  /**
   * The color cadet blue with an RGB value of #5F9EA0
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#5F9EA0;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors CADETBLUE = new Colors(0.37254903f, 0.61960787f, 0.627451f);

  /**
   * The color chartreuse with an RGB value of #7FFF00
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#7FFF00;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors CHARTREUSE = new Colors(0.49803922f, 1.0f, 0.0f);

  /**
   * The color chocolate with an RGB value of #D2691E
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#D2691E;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors CHOCOLATE = new Colors(0.8235294f, 0.4117647f, 0.11764706f);

  /**
   * The color coral with an RGB value of #FF7F50
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#FF7F50;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors CORAL = new Colors(1.0f, 0.49803922f, 0.3137255f);

  /**
   * The color cornflower blue with an RGB value of #6495ED
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#6495ED;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors CORNFLOWERBLUE = new Colors(0.39215687f, 0.58431375f, 0.92941177f);

  /**
   * The color cornsilk with an RGB value of #FFF8DC
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#FFF8DC;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors CORNSILK = new Colors(1.0f, 0.972549f, 0.8627451f);

  /**
   * The color crimson with an RGB value of #DC143C
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#DC143C;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors CRIMSON = new Colors(0.8627451f, 0.078431375f, 0.23529412f);

  /**
   * The color cyan with an RGB value of #00FFFF
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#00FFFF;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors CYAN = new Colors(0.0f, 1.0f, 1.0f);

  /**
   * The color dark blue with an RGB value of #00008B
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#00008B;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors DARKBLUE = new Colors(0.0f, 0.0f, 0.54509807f);

  /**
   * The color dark cyan with an RGB value of #008B8B
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#008B8B;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors DARKCYAN = new Colors(0.0f, 0.54509807f, 0.54509807f);

  /**
   * The color dark goldenrod with an RGB value of #B8860B
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#B8860B;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors DARKGOLDENROD = new Colors(0.72156864f, 0.5254902f, 0.043137256f);

  /**
   * The color dark gray with an RGB value of #A9A9A9
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#A9A9A9;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors DARKGRAY = new Colors(0.6627451f, 0.6627451f, 0.6627451f);

  /**
   * The color dark green with an RGB value of #006400
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#006400;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors DARKGREEN = new Colors(0.0f, 0.39215687f, 0.0f);

  /**
   * The color dark grey with an RGB value of #A9A9A9
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#A9A9A9;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors DARKGREY = Colors.DARKGRAY;

  /**
   * The color dark khaki with an RGB value of #BDB76B
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#BDB76B;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors DARKKHAKI = new Colors(0.7411765f, 0.7176471f, 0.41960785f);

  /**
   * The color dark magenta with an RGB value of #8B008B
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#8B008B;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors DARKMAGENTA = new Colors(0.54509807f, 0.0f, 0.54509807f);

  /**
   * The color dark olive green with an RGB value of #556B2F
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#556B2F;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors DARKOLIVEGREEN = new Colors(0.33333334f, 0.41960785f, 0.18431373f);

  /**
   * The color dark orange with an RGB value of #FF8C00
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#FF8C00;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors DARKORANGE = new Colors(1.0f, 0.54901963f, 0.0f);

  /**
   * The color dark orchid with an RGB value of #9932CC
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#9932CC;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors DARKORCHID = new Colors(0.6f, 0.19607843f, 0.8f);

  /**
   * The color dark red with an RGB value of #8B0000
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#8B0000;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors DARKRED = new Colors(0.54509807f, 0.0f, 0.0f);

  /**
   * The color dark salmon with an RGB value of #E9967A
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#E9967A;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors DARKSALMON = new Colors(0.9137255f, 0.5882353f, 0.47843137f);

  /**
   * The color dark sea green with an RGB value of #8FBC8F
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#8FBC8F;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors DARKSEAGREEN = new Colors(0.56078434f, 0.7372549f, 0.56078434f);

  /**
   * The color dark slate blue with an RGB value of #483D8B
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#483D8B;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors DARKSLATEBLUE = new Colors(0.28235295f, 0.23921569f, 0.54509807f);

  /**
   * The color dark slate gray with an RGB value of #2F4F4F
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#2F4F4F;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors DARKSLATEGRAY = new Colors(0.18431373f, 0.30980393f, 0.30980393f);

  /**
   * The color dark slate grey with an RGB value of #2F4F4F
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#2F4F4F;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors DARKSLATEGREY = Colors.DARKSLATEGRAY;

  /**
   * The color dark turquoise with an RGB value of #00CED1
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#00CED1;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors DARKTURQUOISE = new Colors(0.0f, 0.80784315f, 0.81960785f);

  /**
   * The color dark violet with an RGB value of #9400D3
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#9400D3;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors DARKVIOLET = new Colors(0.5803922f, 0.0f, 0.827451f);

  /**
   * The color deep pink with an RGB value of #FF1493
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#FF1493;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors DEEPPINK = new Colors(1.0f, 0.078431375f, 0.5764706f);

  /**
   * The color deep sky blue with an RGB value of #00BFFF
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#00BFFF;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors DEEPSKYBLUE = new Colors(0.0f, 0.7490196f, 1.0f);

  /**
   * The color dim gray with an RGB value of #696969
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#696969;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors DIMGRAY = new Colors(0.4117647f, 0.4117647f, 0.4117647f);

  /**
   * The color dim grey with an RGB value of #696969
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#696969;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors DIMGREY = Colors.DIMGRAY;

  /**
   * The color dodger blue with an RGB value of #1E90FF
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#1E90FF;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors DODGERBLUE = new Colors(0.11764706f, 0.5647059f, 1.0f);

  /**
   * The color firebrick with an RGB value of #B22222
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#B22222;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors FIREBRICK = new Colors(0.69803923f, 0.13333334f, 0.13333334f);

  /**
   * The color floral white with an RGB value of #FFFAF0
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#FFFAF0;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors FLORALWHITE = new Colors(1.0f, 0.98039216f, 0.9411765f);

  /**
   * The color forest green with an RGB value of #228B22
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#228B22;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors FORESTGREEN = new Colors(0.13333334f, 0.54509807f, 0.13333334f);

  /**
   * The color fuchsia with an RGB value of #FF00FF
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#FF00FF;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors FUCHSIA = new Colors(1.0f, 0.0f, 1.0f);

  /**
   * The color gainsboro with an RGB value of #DCDCDC
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#DCDCDC;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors GAINSBORO = new Colors(0.8627451f, 0.8627451f, 0.8627451f);

  /**
   * The color ghost white with an RGB value of #F8F8FF
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#F8F8FF;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors GHOSTWHITE = new Colors(0.972549f, 0.972549f, 1.0f);

  /**
   * The color gold with an RGB value of #FFD700
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#FFD700;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors GOLD = new Colors(1.0f, 0.84313726f, 0.0f);

  /**
   * The color goldenrod with an RGB value of #DAA520
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#DAA520;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors GOLDENROD = new Colors(0.85490197f, 0.64705884f, 0.1254902f);

  /**
   * The color gray with an RGB value of #808080
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#808080;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors GRAY = new Colors(0.5019608f, 0.5019608f, 0.5019608f);

  /**
   * The color green with an RGB value of #008000
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#008000;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors GREEN = new Colors(0.0f, 0.5019608f, 0.0f);

  /**
   * The color green yellow with an RGB value of #ADFF2F
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#ADFF2F;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors GREENYELLOW = new Colors(0.6784314f, 1.0f, 0.18431373f);

  /**
   * The color grey with an RGB value of #808080
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#808080;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors GREY = Colors.GRAY;

  /**
   * The color honeydew with an RGB value of #F0FFF0
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#F0FFF0;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors HONEYDEW = new Colors(0.9411765f, 1.0f, 0.9411765f);

  /**
   * The color hot pink with an RGB value of #FF69B4
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#FF69B4;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors HOTPINK = new Colors(1.0f, 0.4117647f, 0.7058824f);

  /**
   * The color indian red with an RGB value of #CD5C5C
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#CD5C5C;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors INDIANRED = new Colors(0.8039216f, 0.36078432f, 0.36078432f);

  /**
   * The color indigo with an RGB value of #4B0082
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#4B0082;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors INDIGO = new Colors(0.29411766f, 0.0f, 0.50980395f);

  /**
   * The color ivory with an RGB value of #FFFFF0
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#FFFFF0;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors IVORY = new Colors(1.0f, 1.0f, 0.9411765f);

  /**
   * The color khaki with an RGB value of #F0E68C
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#F0E68C;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors KHAKI = new Colors(0.9411765f, 0.9019608f, 0.54901963f);

  /**
   * The color lavender with an RGB value of #E6E6FA
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#E6E6FA;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors LAVENDER = new Colors(0.9019608f, 0.9019608f, 0.98039216f);

  /**
   * The color lavender blush with an RGB value of #FFF0F5
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#FFF0F5;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors LAVENDERBLUSH = new Colors(1.0f, 0.9411765f, 0.9607843f);

  /**
   * The color lawn green with an RGB value of #7CFC00
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#7CFC00;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors LAWNGREEN = new Colors(0.4862745f, 0.9882353f, 0.0f);

  /**
   * The color lemon chiffon with an RGB value of #FFFACD
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#FFFACD;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors LEMONCHIFFON = new Colors(1.0f, 0.98039216f, 0.8039216f);

  /**
   * The color light blue with an RGB value of #ADD8E6
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#ADD8E6;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors LIGHTBLUE = new Colors(0.6784314f, 0.84705883f, 0.9019608f);

  /**
   * The color light coral with an RGB value of #F08080
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#F08080;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors LIGHTCORAL = new Colors(0.9411765f, 0.5019608f, 0.5019608f);

  /**
   * The color light cyan with an RGB value of #E0FFFF
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#E0FFFF;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors LIGHTCYAN = new Colors(0.8784314f, 1.0f, 1.0f);

  /**
   * The color light goldenrod yellow with an RGB value of #FAFAD2
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#FAFAD2;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors LIGHTGOLDENRODYELLOW = new Colors(0.98039216f, 0.98039216f, 0.8235294f);

  /**
   * The color light gray with an RGB value of #D3D3D3
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#D3D3D3;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors LIGHTGRAY = new Colors(0.827451f, 0.827451f, 0.827451f);

  /**
   * The color light green with an RGB value of #90EE90
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#90EE90;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors LIGHTGREEN = new Colors(0.5647059f, 0.93333334f, 0.5647059f);

  /**
   * The color light grey with an RGB value of #D3D3D3
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#D3D3D3;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors LIGHTGREY = Colors.LIGHTGRAY;

  /**
   * The color light pink with an RGB value of #FFB6C1
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#FFB6C1;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors LIGHTPINK = new Colors(1.0f, 0.7137255f, 0.75686276f);

  /**
   * The color light salmon with an RGB value of #FFA07A
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#FFA07A;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors LIGHTSALMON = new Colors(1.0f, 0.627451f, 0.47843137f);

  /**
   * The color light sea green with an RGB value of #20B2AA
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#20B2AA;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors LIGHTSEAGREEN = new Colors(0.1254902f, 0.69803923f, 0.6666667f);

  /**
   * The color light sky blue with an RGB value of #87CEFA
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#87CEFA;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors LIGHTSKYBLUE = new Colors(0.5294118f, 0.80784315f, 0.98039216f);

  /**
   * The color light slate gray with an RGB value of #778899
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#778899;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors LIGHTSLATEGRAY = new Colors(0.46666667f, 0.53333336f, 0.6f);

  /**
   * The color light slate grey with an RGB value of #778899
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#778899;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors LIGHTSLATEGREY = Colors.LIGHTSLATEGRAY;

  /**
   * The color light steel blue with an RGB value of #B0C4DE
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#B0C4DE;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors LIGHTSTEELBLUE = new Colors(0.6901961f, 0.76862746f, 0.87058824f);

  /**
   * The color light yellow with an RGB value of #FFFFE0
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#FFFFE0;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors LIGHTYELLOW = new Colors(1.0f, 1.0f, 0.8784314f);

  /**
   * The color lime with an RGB value of #00FF00
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#00FF00;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors LIME = new Colors(0.0f, 1.0f, 0.0f);

  /**
   * The color lime green with an RGB value of #32CD32
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#32CD32;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors LIMEGREEN = new Colors(0.19607843f, 0.8039216f, 0.19607843f);

  /**
   * The color linen with an RGB value of #FAF0E6
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#FAF0E6;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors LINEN = new Colors(0.98039216f, 0.9411765f, 0.9019608f);

  /**
   * The color magenta with an RGB value of #FF00FF
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#FF00FF;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors MAGENTA = new Colors(1.0f, 0.0f, 1.0f);

  /**
   * The color maroon with an RGB value of #800000
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#800000;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors MAROON = new Colors(0.5019608f, 0.0f, 0.0f);

  /**
   * The color medium aquamarine with an RGB value of #66CDAA
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#66CDAA;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors MEDIUMAQUAMARINE = new Colors(0.4f, 0.8039216f, 0.6666667f);

  /**
   * The color medium blue with an RGB value of #0000CD
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#0000CD;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors MEDIUMBLUE = new Colors(0.0f, 0.0f, 0.8039216f);

  /**
   * The color medium orchid with an RGB value of #BA55D3
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#BA55D3;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors MEDIUMORCHID = new Colors(0.7294118f, 0.33333334f, 0.827451f);

  /**
   * The color medium purple with an RGB value of #9370DB
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#9370DB;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors MEDIUMPURPLE = new Colors(0.5764706f, 0.4392157f, 0.85882354f);

  /**
   * The color medium sea green with an RGB value of #3CB371
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#3CB371;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors MEDIUMSEAGREEN = new Colors(0.23529412f, 0.7019608f, 0.44313726f);

  /**
   * The color medium slate blue with an RGB value of #7B68EE
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#7B68EE;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors MEDIUMSLATEBLUE = new Colors(0.48235294f, 0.40784314f, 0.93333334f);

  /**
   * The color medium spring green with an RGB value of #00FA9A
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#00FA9A;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors MEDIUMSPRINGGREEN = new Colors(0.0f, 0.98039216f, 0.6039216f);

  /**
   * The color medium turquoise with an RGB value of #48D1CC
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#48D1CC;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors MEDIUMTURQUOISE = new Colors(0.28235295f, 0.81960785f, 0.8f);

  /**
   * The color medium violet red with an RGB value of #C71585
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#C71585;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors MEDIUMVIOLETRED = new Colors(0.78039217f, 0.08235294f, 0.52156866f);

  /**
   * The color midnight blue with an RGB value of #191970
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#191970;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors MIDNIGHTBLUE = new Colors(0.09803922f, 0.09803922f, 0.4392157f);

  /**
   * The color mint cream with an RGB value of #F5FFFA
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#F5FFFA;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors MINTCREAM = new Colors(0.9607843f, 1.0f, 0.98039216f);

  /**
   * The color misty rose with an RGB value of #FFE4E1
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#FFE4E1;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors MISTYROSE = new Colors(1.0f, 0.89411765f, 0.88235295f);

  /**
   * The color moccasin with an RGB value of #FFE4B5
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#FFE4B5;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors MOCCASIN = new Colors(1.0f, 0.89411765f, 0.70980394f);

  /**
   * The color navajo white with an RGB value of #FFDEAD
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#FFDEAD;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors NAVAJOWHITE = new Colors(1.0f, 0.87058824f, 0.6784314f);

  /**
   * The color navy with an RGB value of #000080
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#000080;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors NAVY = new Colors(0.0f, 0.0f, 0.5019608f);

  /**
   * The color old lace with an RGB value of #FDF5E6
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#FDF5E6;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors OLDLACE = new Colors(0.99215686f, 0.9607843f, 0.9019608f);

  /**
   * The color olive with an RGB value of #808000
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#808000;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors OLIVE = new Colors(0.5019608f, 0.5019608f, 0.0f);

  /**
   * The color olive drab with an RGB value of #6B8E23
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#6B8E23;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors OLIVEDRAB = new Colors(0.41960785f, 0.5568628f, 0.13725491f);

  /**
   * The color orange with an RGB value of #FFA500
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#FFA500;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors ORANGE = new Colors(1.0f, 0.64705884f, 0.0f);

  /**
   * The color orange red with an RGB value of #FF4500
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#FF4500;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors ORANGERED = new Colors(1.0f, 0.27058825f, 0.0f);

  /**
   * The color orchid with an RGB value of #DA70D6
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#DA70D6;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors ORCHID = new Colors(0.85490197f, 0.4392157f, 0.8392157f);

  /**
   * The color pale goldenrod with an RGB value of #EEE8AA
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#EEE8AA;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors PALEGOLDENROD = new Colors(0.93333334f, 0.9098039f, 0.6666667f);

  /**
   * The color pale green with an RGB value of #98FB98
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#98FB98;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors PALEGREEN = new Colors(0.59607846f, 0.9843137f, 0.59607846f);

  /**
   * The color pale turquoise with an RGB value of #AFEEEE
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#AFEEEE;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors PALETURQUOISE = new Colors(0.6862745f, 0.93333334f, 0.93333334f);

  /**
   * The color pale violet red with an RGB value of #DB7093
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#DB7093;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors PALEVIOLETRED = new Colors(0.85882354f, 0.4392157f, 0.5764706f);

  /**
   * The color papaya whip with an RGB value of #FFEFD5
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#FFEFD5;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors PAPAYAWHIP = new Colors(1.0f, 0.9372549f, 0.8352941f);

  /**
   * The color peach puff with an RGB value of #FFDAB9
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#FFDAB9;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors PEACHPUFF = new Colors(1.0f, 0.85490197f, 0.7254902f);

  /**
   * The color peru with an RGB value of #CD853F
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#CD853F;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors PERU = new Colors(0.8039216f, 0.52156866f, 0.24705882f);

  /**
   * The color pink with an RGB value of #FFC0CB
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#FFC0CB;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors PINK = new Colors(1.0f, 0.7529412f, 0.79607844f);

  /**
   * The color plum with an RGB value of #DDA0DD
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#DDA0DD;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors PLUM = new Colors(0.8666667f, 0.627451f, 0.8666667f);

  /**
   * The color powder blue with an RGB value of #B0E0E6
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#B0E0E6;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors POWDERBLUE = new Colors(0.6901961f, 0.8784314f, 0.9019608f);

  /**
   * The color purple with an RGB value of #800080
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#800080;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors PURPLE = new Colors(0.5019608f, 0.0f, 0.5019608f);

  /**
   * The color red with an RGB value of #FF0000
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#FF0000;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors RED = new Colors(1.0f, 0.0f, 0.0f);

  /**
   * The color rosy brown with an RGB value of #BC8F8F
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#BC8F8F;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors ROSYBROWN = new Colors(0.7372549f, 0.56078434f, 0.56078434f);

  /**
   * The color royal blue with an RGB value of #4169E1
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#4169E1;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors ROYALBLUE = new Colors(0.25490198f, 0.4117647f, 0.88235295f);

  /**
   * The color saddle brown with an RGB value of #8B4513
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#8B4513;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors SADDLEBROWN = new Colors(0.54509807f, 0.27058825f, 0.07450981f);

  /**
   * The color salmon with an RGB value of #FA8072
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#FA8072;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors SALMON = new Colors(0.98039216f, 0.5019608f, 0.44705883f);

  /**
   * The color sandy brown with an RGB value of #F4A460
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#F4A460;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors SANDYBROWN = new Colors(0.95686275f, 0.6431373f, 0.3764706f);

  /**
   * The color sea green with an RGB value of #2E8B57
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#2E8B57;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors SEAGREEN = new Colors(0.18039216f, 0.54509807f, 0.34117648f);

  /**
   * The color sea shell with an RGB value of #FFF5EE
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#FFF5EE;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors SEASHELL = new Colors(1.0f, 0.9607843f, 0.93333334f);

  /**
   * The color sienna with an RGB value of #A0522D
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#A0522D;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors SIENNA = new Colors(0.627451f, 0.32156864f, 0.1764706f);

  /**
   * The color silver with an RGB value of #C0C0C0
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#C0C0C0;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors SILVER = new Colors(0.7529412f, 0.7529412f, 0.7529412f);

  /**
   * The color sky blue with an RGB value of #87CEEB
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#87CEEB;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors SKYBLUE = new Colors(0.5294118f, 0.80784315f, 0.92156863f);

  /**
   * The color slate blue with an RGB value of #6A5ACD
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#6A5ACD;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors SLATEBLUE = new Colors(0.41568628f, 0.3529412f, 0.8039216f);

  /**
   * The color slate gray with an RGB value of #708090
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#708090;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors SLATEGRAY = new Colors(0.4392157f, 0.5019608f, 0.5647059f);

  /**
   * The color slate grey with an RGB value of #708090
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#708090;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors SLATEGREY = Colors.SLATEGRAY;

  /**
   * The color snow with an RGB value of #FFFAFA
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#FFFAFA;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors SNOW = new Colors(1.0f, 0.98039216f, 0.98039216f);

  /**
   * The color spring green with an RGB value of #00FF7F
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#00FF7F;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors SPRINGGREEN = new Colors(0.0f, 1.0f, 0.49803922f);

  /**
   * The color steel blue with an RGB value of #4682B4
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#4682B4;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors STEELBLUE = new Colors(0.27450982f, 0.50980395f, 0.7058824f);

  /**
   * The color tan with an RGB value of #D2B48C
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#D2B48C;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors TAN = new Colors(0.8235294f, 0.7058824f, 0.54901963f);

  /**
   * The color teal with an RGB value of #008080
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#008080;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors TEAL = new Colors(0.0f, 0.5019608f, 0.5019608f);

  /**
   * The color thistle with an RGB value of #D8BFD8
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#D8BFD8;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors THISTLE = new Colors(0.84705883f, 0.7490196f, 0.84705883f);

  /**
   * The color tomato with an RGB value of #FF6347
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#FF6347;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors TOMATO = new Colors(1.0f, 0.3882353f, 0.2784314f);

  /**
   * A fully transparent color with an ARGB value of #00000000.
   */
  public static final Colors TRANSPARENT = new Colors(0f, 0f, 0f, 0f);

  /**
   * The color turquoise with an RGB value of #40E0D0
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#40E0D0;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors TURQUOISE = new Colors(0.2509804f, 0.8784314f, 0.8156863f);

  /**
   * The color violet with an RGB value of #EE82EE
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#EE82EE;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors VIOLET = new Colors(0.93333334f, 0.50980395f, 0.93333334f);

  /**
   * The color wheat with an RGB value of #F5DEB3
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#F5DEB3;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors WHEAT = new Colors(0.9607843f, 0.87058824f, 0.7019608f);

  /**
   * The color white with an RGB value of #FFFFFF
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#FFFFFF;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors WHITE = new Colors(1.0f, 1.0f, 1.0f);

  /**
   * The color white smoke with an RGB value of #F5F5F5
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#F5F5F5;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors WHITESMOKE = new Colors(0.9607843f, 0.9607843f, 0.9607843f);

  /**
   * The color yellow with an RGB value of #FFFF00
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#FFFF00;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors YELLOW = new Colors(1.0f, 1.0f, 0.0f);

  /**
   * The color yellow green with an RGB value of #9ACD32
   * <div style="border:1px solid black;width:40px;height:20px;background-color:#9ACD32;float:right;margin: 0 10px 0 0"></div><br/><br/>
   */
  public static final Colors YELLOWGREEN = new Colors(0.6039216f, 0.8039216f, 0.19607843f);

  private static final int PARSE_ALPHA = 3; // clamped to [0.0,1.0]

  private static final int PARSE_ANGLE = 2; // clamped to [0,360]

  private static final int PARSE_COMPONENT = 0; // percent, or clamped to [0,255] => [0,1]

  private static final int PARSE_PERCENT = 1; // clamped to [0,100]% => [0,1]

  private final float blue;

  private final float green;

  private final float red;

  private float opacity = 1;

  private Object platformPaint;

  public Colors(final double red, final double green, final double blue, final double opacity) {
    if (red < 0 || red > 1) {
      throw new IllegalArgumentException("Color's red value (" + red + ") must be in the range 0.0-1.0");
    }
    if (green < 0 || green > 1) {
      throw new IllegalArgumentException("Color's green value (" + green + ") must be in the range 0.0-1.0");
    }
    if (blue < 0 || blue > 1) {
      throw new IllegalArgumentException("Color's blue value (" + blue + ") must be in the range 0.0-1.0");
    }
    if (opacity < 0 || opacity > 1) {
      throw new IllegalArgumentException("Color's opacity value (" + opacity + ") must be in the range 0.0-1.0");
    }
    this.red = (float) red;
    this.green = (float) green;
    this.blue = (float) blue;
    this.opacity = (float) opacity;
  }

  private Colors(final float red, final float green, final float blue) {
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  public static double[] HSBtoRGB(double hue, final double saturation, final double brightness) {
    // normalize the hue
    final double normalizedHue = (hue % 360 + 360) % 360;
    hue = normalizedHue / 360;
    double r = 0, g = 0, b = 0;
    if (saturation == 0) {
      r = g = b = brightness;
    } else {
      final double h = (hue - Math.floor(hue)) * 6.0;
      final double f = h - Math.floor(h);
      final double p = brightness * (1.0 - saturation);
      final double q = brightness * (1.0 - saturation * f);
      final double t = brightness * (1.0 - saturation * (1.0 - f));
      switch ((int) h) {
        case 0:
          r = brightness;
          g = t;
          b = p;
          break;
        case 1:
          r = q;
          g = brightness;
          b = p;
          break;
        case 2:
          r = p;
          g = brightness;
          b = t;
          break;
        case 3:
          r = p;
          g = q;
          b = brightness;
          break;
        case 4:
          r = t;
          g = p;
          b = brightness;
          break;
        case 5:
          r = brightness;
          g = p;
          b = q;
          break;
      }
    }
    final double[] f = new double[3];
    f[0] = r;
    f[1] = g;
    f[2] = b;
    return f;
  }

  public static double[] RGBtoHSB(final double r, final double g, final double b) {
    double hue;
    final double saturation;
    final double brightness;
    final double[] hsbvals = new double[3];
    double cmax = r > g ? r : g;
    if (b > cmax) {
      cmax = b;
    }
    double cmin = r < g ? r : g;
    if (b < cmin) {
      cmin = b;
    }
    brightness = cmax;
    if (cmax != 0) {
      saturation = (cmax - cmin) / cmax;
    } else {
      saturation = 0;
    }
    if (saturation == 0) {
      hue = 0;
    } else {
      final double redc = (cmax - r) / (cmax - cmin);
      final double greenc = (cmax - g) / (cmax - cmin);
      final double bluec = (cmax - b) / (cmax - cmin);
      if (r == cmax) {
        hue = bluec - greenc;
      } else if (g == cmax) {
        hue = 2.0 + redc - bluec;
      } else {
        hue = 4.0 + greenc - redc;
      }
      hue = hue / 6.0;
      if (hue < 0) {
        hue = hue + 1.0;
      }
    }
    hsbvals[0] = hue * 360;
    hsbvals[1] = saturation;
    hsbvals[2] = brightness;
    return hsbvals;
  }

  public static Colors color(final double red, final double green, final double blue, final double opacity) {
    return new Colors(red, green, blue, opacity);
  }

  public static Colors hsb(final double hue, final double saturation, final double brightness, final double opacity) {
    Colors.checkSB(saturation, brightness);
    final double[] rgb = Colors.HSBtoRGB(hue, saturation, brightness);
    final Colors result = new Colors(rgb[0], rgb[1], rgb[2], opacity);
    return result;
  }

  public static Colors rgb(final int red, final int green, final int blue, final double opacity) {
    Colors.checkRGB(red, green, blue);
    return new Colors(
      red / 255.0,
      green / 255.0,
      blue / 255.0,
      opacity);
  }

  public static Colors valueOf(final String value) {
    if (value == null) {
      throw new NullPointerException("color must be specified");
    }
    return Colors.web(value);
  }

  public static Colors web(final String colorString, final double opacity) {
    if (colorString == null) {
      throw new NullPointerException(
        "The color components or name must be specified");
    }
    if (colorString.isEmpty()) {
      throw new IllegalArgumentException("Invalid color specification");
    }
    String color = colorString.toLowerCase(Locale.ROOT);
    if (color.startsWith("#")) {
      color = color.substring(1);
    } else if (color.startsWith("0x")) {
      color = color.substring(2);
    } else if (color.startsWith("rgb")) {
      if (color.startsWith("(", 3)) {
        return Colors.parseRGBColor(color, 4, false, opacity);
      } else if (color.startsWith("a(", 3)) {
        return Colors.parseRGBColor(color, 5, true, opacity);
      }
    } else if (color.startsWith("hsl")) {
      if (color.startsWith("(", 3)) {
        return Colors.parseHSLColor(color, 4, false, opacity);
      } else if (color.startsWith("a(", 3)) {
        return Colors.parseHSLColor(color, 5, true, opacity);
      }
    } else {
      final Colors col = NamedColors.get(color);
      if (col != null) {
        if (opacity == 1.0) {
          return col;
        } else {
          return Colors.color(col.red, col.green, col.blue, opacity);
        }
      }
    }
    final int len = color.length();
    try {
      final int r;
      final int g;
      final int b;
      final int a;
      if (len == 3) {
        r = Integer.parseInt(color.substring(0, 1), 16);
        g = Integer.parseInt(color.substring(1, 2), 16);
        b = Integer.parseInt(color.substring(2, 3), 16);
        return Colors.color(r / 15.0, g / 15.0, b / 15.0, opacity);
      } else if (len == 4) {
        r = Integer.parseInt(color.substring(0, 1), 16);
        g = Integer.parseInt(color.substring(1, 2), 16);
        b = Integer.parseInt(color.substring(2, 3), 16);
        a = Integer.parseInt(color.substring(3, 4), 16);
        return Colors.color(r / 15.0, g / 15.0, b / 15.0,
          opacity * a / 15.0);
      } else if (len == 6) {
        r = Integer.parseInt(color.substring(0, 2), 16);
        g = Integer.parseInt(color.substring(2, 4), 16);
        b = Integer.parseInt(color.substring(4, 6), 16);
        return Colors.rgb(r, g, b, opacity);
      } else if (len == 8) {
        r = Integer.parseInt(color.substring(0, 2), 16);
        g = Integer.parseInt(color.substring(2, 4), 16);
        b = Integer.parseInt(color.substring(4, 6), 16);
        a = Integer.parseInt(color.substring(6, 8), 16);
        return Colors.rgb(r, g, b, opacity * a / 255.0);
      }
    } catch (final NumberFormatException nfe) {
    }
    throw new IllegalArgumentException("Invalid color specification");
  }

  public static Colors web(final String colorString) {
    return Colors.web(colorString, 1.0);
  }

  private static void checkRGB(final int red, final int green, final int blue) {
    if (red < 0 || red > 255) {
      throw new IllegalArgumentException("Color.rgb's red parameter (" + red + ") expects color values 0-255");
    }
    if (green < 0 || green > 255) {
      throw new IllegalArgumentException("Color.rgb's green parameter (" + green + ") expects color values 0-255");
    }
    if (blue < 0 || blue > 255) {
      throw new IllegalArgumentException("Color.rgb's blue parameter (" + blue + ") expects color values 0-255");
    }
  }

  private static void checkSB(final double saturation, final double brightness) {
    if (saturation < 0.0 || saturation > 1.0) {
      throw new IllegalArgumentException("Color.hsb's saturation parameter (" + saturation + ") expects values 0.0-1.0");
    }
    if (brightness < 0.0 || brightness > 1.0) {
      throw new IllegalArgumentException("Color.hsb's brightness parameter (" + brightness + ") expects values 0.0-1.0");
    }
  }

  private static double parseComponent(String color, final int off, final int end, int type) {
    color = color.substring(off, end).trim();
    if (color.endsWith("%")) {
      if (type > Colors.PARSE_PERCENT) {
        throw new IllegalArgumentException("Invalid color specification");
      }
      type = Colors.PARSE_PERCENT;
      color = color.substring(0, color.length() - 1).trim();
    } else if (type == Colors.PARSE_PERCENT) {
      throw new IllegalArgumentException("Invalid color specification");
    }
    final double c = type == Colors.PARSE_COMPONENT
      ? Integer.parseInt(color)
      : Double.parseDouble(color);
    switch (type) {
      case Colors.PARSE_ALPHA:
        return c < 0.0 ? 0.0 : c > 1.0 ? 1.0 : c;
      case Colors.PARSE_PERCENT:
        return c <= 0.0 ? 0.0 : c >= 100.0 ? 1.0 : c / 100.0;
      case Colors.PARSE_COMPONENT:
        return c <= 0.0 ? 0.0 : c >= 255.0 ? 1.0 : c / 255.0;
      case Colors.PARSE_ANGLE:
        return c < 0.0
          ? c % 360.0 + 360.0
          : c > 360.0
          ? c % 360.0
          : c;
    }
    throw new IllegalArgumentException("Invalid color specification");
  }

  private static Colors parseHSLColor(final String color, final int hoff,
                                      final boolean hasAlpha, double a) {
    try {
      final int hend = color.indexOf(',', hoff);
      final int send = hend < 0 ? -1 : color.indexOf(',', hend + 1);
      final int lend = send < 0 ? -1 : color.indexOf(hasAlpha ? ',' : ')', send + 1);
      final int aend = hasAlpha ? lend < 0 ? -1 : color.indexOf(')', lend + 1) : lend;
      if (aend >= 0) {
        final double h = Colors.parseComponent(color, hoff, hend, Colors.PARSE_ANGLE);
        final double s = Colors.parseComponent(color, hend + 1, send, Colors.PARSE_PERCENT);
        final double l = Colors.parseComponent(color, send + 1, lend, Colors.PARSE_PERCENT);
        if (hasAlpha) {
          a *= Colors.parseComponent(color, lend + 1, aend, Colors.PARSE_ALPHA);
        }
        return Colors.hsb(h, s, l, a);
      }
    } catch (final NumberFormatException nfe) {
    }
    throw new IllegalArgumentException("Invalid color specification");
  }

  private static Colors parseRGBColor(final String color, final int roff,
                                      final boolean hasAlpha, double a) {
    try {
      final int rend = color.indexOf(',', roff);
      final int gend = rend < 0 ? -1 : color.indexOf(',', rend + 1);
      final int bend = gend < 0 ? -1 : color.indexOf(hasAlpha ? ',' : ')', gend + 1);
      final int aend = hasAlpha ? bend < 0 ? -1 : color.indexOf(')', bend + 1) : bend;
      if (aend >= 0) {
        final double r = Colors.parseComponent(color, roff, rend, Colors.PARSE_COMPONENT);
        final double g = Colors.parseComponent(color, rend + 1, gend, Colors.PARSE_COMPONENT);
        final double b = Colors.parseComponent(color, gend + 1, bend, Colors.PARSE_COMPONENT);
        if (hasAlpha) {
          a *= Colors.parseComponent(color, bend + 1, aend, Colors.PARSE_ALPHA);
        }
        return new Colors(r, g, b, a);
      }
    } catch (final NumberFormatException nfe) {
    }
    throw new IllegalArgumentException("Invalid color specification");
  }

  private static int to32BitInteger(final int red, final int green, final int blue, final int alpha) {
    int i = red;
    i = i << 8;
    i = i | green;
    i = i << 8;
    i = i | blue;
    i = i << 8;
    i = i | alpha;
    return i;
  }

  public final double getBlue() {
    return this.blue;
  }

  public final double getGreen() {
    return this.green;
  }

  public final double getOpacity() {
    return this.opacity;
  }

  public final double getRed() {
    return this.red;
  }

  /**
   * @inheritDoc
   * @since JavaFX 8.0
   */
  public final boolean isOpaque() {
    return this.opacity >= 1f;
  }

  /**
   * Gets the brightness component of this {@code Color}.
   *
   * @return Brightness value in the range in the range {@code 0.0-1.0}.
   */
  public double getBrightness() {
    return Colors.RGBtoHSB(this.red, this.green, this.blue)[2];
  }

  /**
   * Gets the hue component of this {@code Color}.
   *
   * @return Hue value in the range in the range {@code 0.0-360.0}.
   */
  public double getHue() {
    return Colors.RGBtoHSB(this.red, this.green, this.blue)[0];
  }

  /**
   * Gets the saturation component of this {@code Color}.
   *
   * @return Saturation value in the range in the range {@code 0.0-1.0}.
   */
  public double getSaturation() {
    return Colors.RGBtoHSB(this.red, this.green, this.blue)[1];
  }

  @Override
  public int hashCode() {
    // construct the 32bit integer representation of this color
    final int r = (int) Math.round(this.red * 255.0);
    final int g = (int) Math.round(this.green * 255.0);
    final int b = (int) Math.round(this.blue * 255.0);
    final int a = (int) Math.round(this.opacity * 255.0);
    return Colors.to32BitInteger(r, g, b, a);
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj instanceof Colors) {
      final Colors other = (Colors) obj;
      return this.red == other.red
        && this.green == other.green
        && this.blue == other.blue
        && this.opacity == other.opacity;
    } else {
      return false;
    }
  }

  @Override
  public String toString() {
    final int r = (int) Math.round(this.red * 255.0);
    final int g = (int) Math.round(this.green * 255.0);
    final int b = (int) Math.round(this.blue * 255.0);
    final int o = (int) Math.round(this.opacity * 255.0);
    return String.format("0x%02x%02x%02x%02x", r, g, b, o);
  }

  public Colors invert() {
    return Colors.color(1.0 - this.red, 1.0 - this.green, 1.0 - this.blue, this.opacity);
  }

  private static final class NamedColors {

    private static final Map<String, Colors> namedColors =
      NamedColors.createNamedColors();

    private NamedColors() {
    }

    private static Map<String, Colors> createNamedColors() {
      final Map<String, Colors> colors = new HashMap<>(256);
      colors.put("aliceblue", Colors.ALICEBLUE);
      colors.put("antiquewhite", Colors.ANTIQUEWHITE);
      colors.put("aqua", Colors.AQUA);
      colors.put("aquamarine", Colors.AQUAMARINE);
      colors.put("azure", Colors.AZURE);
      colors.put("beige", Colors.BEIGE);
      colors.put("bisque", Colors.BISQUE);
      colors.put("black", Colors.BLACK);
      colors.put("blanchedalmond", Colors.BLANCHEDALMOND);
      colors.put("blue", Colors.BLUE);
      colors.put("blueviolet", Colors.BLUEVIOLET);
      colors.put("brown", Colors.BROWN);
      colors.put("burlywood", Colors.BURLYWOOD);
      colors.put("cadetblue", Colors.CADETBLUE);
      colors.put("chartreuse", Colors.CHARTREUSE);
      colors.put("chocolate", Colors.CHOCOLATE);
      colors.put("coral", Colors.CORAL);
      colors.put("cornflowerblue", Colors.CORNFLOWERBLUE);
      colors.put("cornsilk", Colors.CORNSILK);
      colors.put("crimson", Colors.CRIMSON);
      colors.put("cyan", Colors.CYAN);
      colors.put("darkblue", Colors.DARKBLUE);
      colors.put("darkcyan", Colors.DARKCYAN);
      colors.put("darkgoldenrod", Colors.DARKGOLDENROD);
      colors.put("darkgray", Colors.DARKGRAY);
      colors.put("darkgreen", Colors.DARKGREEN);
      colors.put("darkgrey", Colors.DARKGREY);
      colors.put("darkkhaki", Colors.DARKKHAKI);
      colors.put("darkmagenta", Colors.DARKMAGENTA);
      colors.put("darkolivegreen", Colors.DARKOLIVEGREEN);
      colors.put("darkorange", Colors.DARKORANGE);
      colors.put("darkorchid", Colors.DARKORCHID);
      colors.put("darkred", Colors.DARKRED);
      colors.put("darksalmon", Colors.DARKSALMON);
      colors.put("darkseagreen", Colors.DARKSEAGREEN);
      colors.put("darkslateblue", Colors.DARKSLATEBLUE);
      colors.put("darkslategray", Colors.DARKSLATEGRAY);
      colors.put("darkslategrey", Colors.DARKSLATEGREY);
      colors.put("darkturquoise", Colors.DARKTURQUOISE);
      colors.put("darkviolet", Colors.DARKVIOLET);
      colors.put("deeppink", Colors.DEEPPINK);
      colors.put("deepskyblue", Colors.DEEPSKYBLUE);
      colors.put("dimgray", Colors.DIMGRAY);
      colors.put("dimgrey", Colors.DIMGREY);
      colors.put("dodgerblue", Colors.DODGERBLUE);
      colors.put("firebrick", Colors.FIREBRICK);
      colors.put("floralwhite", Colors.FLORALWHITE);
      colors.put("forestgreen", Colors.FORESTGREEN);
      colors.put("fuchsia", Colors.FUCHSIA);
      colors.put("gainsboro", Colors.GAINSBORO);
      colors.put("ghostwhite", Colors.GHOSTWHITE);
      colors.put("gold", Colors.GOLD);
      colors.put("goldenrod", Colors.GOLDENROD);
      colors.put("gray", Colors.GRAY);
      colors.put("green", Colors.GREEN);
      colors.put("greenyellow", Colors.GREENYELLOW);
      colors.put("grey", Colors.GREY);
      colors.put("honeydew", Colors.HONEYDEW);
      colors.put("hotpink", Colors.HOTPINK);
      colors.put("indianred", Colors.INDIANRED);
      colors.put("indigo", Colors.INDIGO);
      colors.put("ivory", Colors.IVORY);
      colors.put("khaki", Colors.KHAKI);
      colors.put("lavender", Colors.LAVENDER);
      colors.put("lavenderblush", Colors.LAVENDERBLUSH);
      colors.put("lawngreen", Colors.LAWNGREEN);
      colors.put("lemonchiffon", Colors.LEMONCHIFFON);
      colors.put("lightblue", Colors.LIGHTBLUE);
      colors.put("lightcoral", Colors.LIGHTCORAL);
      colors.put("lightcyan", Colors.LIGHTCYAN);
      colors.put("lightgoldenrodyellow", Colors.LIGHTGOLDENRODYELLOW);
      colors.put("lightgray", Colors.LIGHTGRAY);
      colors.put("lightgreen", Colors.LIGHTGREEN);
      colors.put("lightgrey", Colors.LIGHTGREY);
      colors.put("lightpink", Colors.LIGHTPINK);
      colors.put("lightsalmon", Colors.LIGHTSALMON);
      colors.put("lightseagreen", Colors.LIGHTSEAGREEN);
      colors.put("lightskyblue", Colors.LIGHTSKYBLUE);
      colors.put("lightslategray", Colors.LIGHTSLATEGRAY);
      colors.put("lightslategrey", Colors.LIGHTSLATEGREY);
      colors.put("lightsteelblue", Colors.LIGHTSTEELBLUE);
      colors.put("lightyellow", Colors.LIGHTYELLOW);
      colors.put("lime", Colors.LIME);
      colors.put("limegreen", Colors.LIMEGREEN);
      colors.put("linen", Colors.LINEN);
      colors.put("magenta", Colors.MAGENTA);
      colors.put("maroon", Colors.MAROON);
      colors.put("mediumaquamarine", Colors.MEDIUMAQUAMARINE);
      colors.put("mediumblue", Colors.MEDIUMBLUE);
      colors.put("mediumorchid", Colors.MEDIUMORCHID);
      colors.put("mediumpurple", Colors.MEDIUMPURPLE);
      colors.put("mediumseagreen", Colors.MEDIUMSEAGREEN);
      colors.put("mediumslateblue", Colors.MEDIUMSLATEBLUE);
      colors.put("mediumspringgreen", Colors.MEDIUMSPRINGGREEN);
      colors.put("mediumturquoise", Colors.MEDIUMTURQUOISE);
      colors.put("mediumvioletred", Colors.MEDIUMVIOLETRED);
      colors.put("midnightblue", Colors.MIDNIGHTBLUE);
      colors.put("mintcream", Colors.MINTCREAM);
      colors.put("mistyrose", Colors.MISTYROSE);
      colors.put("moccasin", Colors.MOCCASIN);
      colors.put("navajowhite", Colors.NAVAJOWHITE);
      colors.put("navy", Colors.NAVY);
      colors.put("oldlace", Colors.OLDLACE);
      colors.put("olive", Colors.OLIVE);
      colors.put("olivedrab", Colors.OLIVEDRAB);
      colors.put("orange", Colors.ORANGE);
      colors.put("orangered", Colors.ORANGERED);
      colors.put("orchid", Colors.ORCHID);
      colors.put("palegoldenrod", Colors.PALEGOLDENROD);
      colors.put("palegreen", Colors.PALEGREEN);
      colors.put("paleturquoise", Colors.PALETURQUOISE);
      colors.put("palevioletred", Colors.PALEVIOLETRED);
      colors.put("papayawhip", Colors.PAPAYAWHIP);
      colors.put("peachpuff", Colors.PEACHPUFF);
      colors.put("peru", Colors.PERU);
      colors.put("pink", Colors.PINK);
      colors.put("plum", Colors.PLUM);
      colors.put("powderblue", Colors.POWDERBLUE);
      colors.put("purple", Colors.PURPLE);
      colors.put("red", Colors.RED);
      colors.put("rosybrown", Colors.ROSYBROWN);
      colors.put("royalblue", Colors.ROYALBLUE);
      colors.put("saddlebrown", Colors.SADDLEBROWN);
      colors.put("salmon", Colors.SALMON);
      colors.put("sandybrown", Colors.SANDYBROWN);
      colors.put("seagreen", Colors.SEAGREEN);
      colors.put("seashell", Colors.SEASHELL);
      colors.put("sienna", Colors.SIENNA);
      colors.put("silver", Colors.SILVER);
      colors.put("skyblue", Colors.SKYBLUE);
      colors.put("slateblue", Colors.SLATEBLUE);
      colors.put("slategray", Colors.SLATEGRAY);
      colors.put("slategrey", Colors.SLATEGREY);
      colors.put("snow", Colors.SNOW);
      colors.put("springgreen", Colors.SPRINGGREEN);
      colors.put("steelblue", Colors.STEELBLUE);
      colors.put("tan", Colors.TAN);
      colors.put("teal", Colors.TEAL);
      colors.put("thistle", Colors.THISTLE);
      colors.put("tomato", Colors.TOMATO);
      colors.put("transparent", Colors.TRANSPARENT);
      colors.put("turquoise", Colors.TURQUOISE);
      colors.put("violet", Colors.VIOLET);
      colors.put("wheat", Colors.WHEAT);
      colors.put("white", Colors.WHITE);
      colors.put("whitesmoke", Colors.WHITESMOKE);
      colors.put("yellow", Colors.YELLOW);
      colors.put("yellowgreen", Colors.YELLOWGREEN);
      return colors;
    }

    private static Colors get(final String name) {
      return NamedColors.namedColors.get(name);
    }
  }
}
