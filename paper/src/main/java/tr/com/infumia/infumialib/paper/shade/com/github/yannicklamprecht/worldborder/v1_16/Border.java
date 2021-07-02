package tr.com.infumia.infumialib.paper.shade.com.github.yannicklamprecht.worldborder.v1_16;

import tr.com.infumia.infumialib.paper.shade.com.github.yannicklamprecht.worldborder.api.WorldBorderApiImpl;

public class Border extends WorldBorderApiImpl {

  public Border() {
    super(WorldBorder::new, WorldBorder::new);
  }
}
