package org.batfish.representation.cisco;

import java.util.List;
import java.util.Set;
import org.batfish.common.Warnings;
import org.batfish.datamodel.Configuration;
import org.batfish.datamodel.IpAccessList;
import org.batfish.datamodel.RouteFilterList;
import org.batfish.datamodel.routing_policy.expr.BooleanExpr;
import org.batfish.datamodel.routing_policy.expr.DestinationNetwork;
import org.batfish.datamodel.routing_policy.expr.Disjunction;
import org.batfish.datamodel.routing_policy.expr.MatchIpAccessList;
import org.batfish.datamodel.routing_policy.expr.MatchPrefixSet;
import org.batfish.datamodel.routing_policy.expr.NamedPrefixSet;

public class RouteMapMatchIpAccessListLine extends RouteMapMatchLine {

  private static final long serialVersionUID = 1L;

  private final Set<String> _listNames;

  private boolean _routing;

  public RouteMapMatchIpAccessListLine(Set<String> listNames) {
    _listNames = listNames;
  }

  public Set<String> getListNames() {
    return _listNames;
  }

  public boolean getRouting() {
    return _routing;
  }

  public void setRouting(boolean routing) {
    _routing = routing;
  }

  @Override
  public BooleanExpr toBooleanExpr(Configuration c, CiscoConfiguration cc, Warnings w) {
    Disjunction d = new Disjunction();
    List<BooleanExpr> disjuncts = d.getDisjuncts();
    for (String listName : _listNames) {
      if (_routing) {
        RouteFilterList routeFilterList = c.getRouteFilterLists().get(listName);
        if (routeFilterList != null) {
          disjuncts.add(
              new MatchPrefixSet(DestinationNetwork.instance(), new NamedPrefixSet(listName)));
        }
      } else {
        IpAccessList ipAccessList = c.getIpAccessLists().get(listName);
        if (ipAccessList != null) {
          disjuncts.add(new MatchIpAccessList(listName));
        }
      }
    }
    return d.simplify();
  }
}
