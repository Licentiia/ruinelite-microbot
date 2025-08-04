package net.runelite.client.plugins.ruinelite.plugins.autobankpin;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.microbot.Microbot;
import net.runelite.client.plugins.microbot.pluginscheduler.api.SchedulablePlugin;
import net.runelite.client.plugins.microbot.pluginscheduler.condition.logical.AndCondition;
import net.runelite.client.plugins.microbot.pluginscheduler.condition.logical.LogicalCondition;
import net.runelite.client.plugins.microbot.pluginscheduler.event.PluginScheduleEntrySoftStopEvent;

import javax.inject.Inject;

@Slf4j
@PluginDescriptor(
	name = PluginDescriptor.SnifdenSplif + "Auto Bank Pin",
	description = "Automatically enters your bank pin when the bank pin screen appears.",
	tags = {"bank", "pin", "auto", "microbot", "ruinelite"},
	enabledByDefault = false
)
public class AutoBankPinPlugin extends Plugin implements SchedulablePlugin {

	@Inject
	private AutoBankPinConfig config;

	@Inject
	private AutoBankPinScript script;

	private final LogicalCondition stopCondition = new AndCondition();

	@Provides
	AutoBankPinConfig provideConfig(ConfigManager configManager) {
		return configManager.getConfig(AutoBankPinConfig.class);
	}

	@Override
	protected void startUp() {
		script.setConfig(config);
		script.run();
	}

	@Override
	protected void shutDown() {
		script.shutdown();
	}

	@Subscribe
	public void onPluginScheduleEntrySoftStopEvent(PluginScheduleEntrySoftStopEvent event) {
		if (event.getPlugin() == this) {
			script.shutdown();
			Microbot.stopPlugin(this);
		}
	}

	@Override
	public LogicalCondition getStopCondition() {
		return stopCondition;
	}
}
