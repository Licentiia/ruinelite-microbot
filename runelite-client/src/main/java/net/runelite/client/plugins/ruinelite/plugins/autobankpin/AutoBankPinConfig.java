package net.runelite.client.plugins.ruinelite.plugins.autobankpin;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("autobankpin")
public interface AutoBankPinConfig extends Config {

	@ConfigItem(
		keyName = "bankPin",
		name = "Bank PIN",
		description = "Enter your 4-digit bank pin (digits only, no spaces)",
		secret = true
	)
	default String bankPin() {
		return "";
	}
}

