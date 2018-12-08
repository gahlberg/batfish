package org.batfish.bgp;

import com.google.auto.service.AutoService;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSet;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import org.batfish.common.plugin.ExternalBgpAdvertisementPlugin;
import org.batfish.common.plugin.Plugin;
import org.batfish.datamodel.BgpAdvertisement;
import org.batfish.datamodel.bgp.EbgpAdvertisementGroup;

@AutoService(Plugin.class)
@ParametersAreNonnullByDefault
public class BgpAdvertisementGroupPlugin extends ExternalBgpAdvertisementPlugin {

  @Override
  protected void externalBgpAdvertisementPluginInitialize() {}

  @Override
  public Set<BgpAdvertisement> loadExternalBgpAdvertisements() {
    return _batfish
        .loadEbgpAdvertisementGroups()
        .stream()
        .map(this::toAdvertisements)
        .flatMap(Collection::stream)
        .collect(ImmutableSet.toImmutableSet());
  }

  private @Nonnull List<BgpAdvertisement> toAdvertisements(EbgpAdvertisementGroup group) {
    BgpAdvertisement.Builder builder =
        BgpAdvertisement.builder()
            .setAsPath(group.getAsPath())
            .setCommunities(ImmutableSortedSet.copyOf(group.getStandardCommunities()))
            .setDstIp(group.getRxPeer())
            .setLocalPreference(group.getLocalPreference());
  }
}
