package net.runelite.client.plugins.ruinelite.plugins.autobankpin;

import net.runelite.client.plugins.microbot.Microbot;
import net.runelite.client.plugins.microbot.Script;
import net.runelite.client.plugins.microbot.util.bank.Rs2Bank;

import java.util.concurrent.TimeUnit;

public class AutoBankPinScript extends Script {

	private AutoBankPinConfig config;

	public void setConfig(AutoBankPinConfig config) {
		this.config = config;
	}

	@Override
	public boolean run() {
		mainScheduledFuture = scheduledExecutorService.scheduleWithFixedDelay(() -> {
			try {
				if (!Microbot.isLoggedIn()) return;

				if (Rs2Bank.isBankPinWidgetVisible()) {
					String pin = config.bankPin();
					if (pin != null && pin.length() == 4 && pin.matches("\\d{4}")) {
						Rs2Bank.handleBankPin(pin);
						sleepUntil(() -> !Rs2Bank.isBankPinWidgetVisible(), 5000);
					} else {
						Microbot.log("Invalid or missing bank pin in config.");
					}
				}
			} catch (Exception e) {
				Microbot.log("AutoBankPin error: " + e.getMessage());
			}
		}, 0, 500, TimeUnit.MILLISECONDS);

		return true;
	}

	@Override
	public void shutdown() {
		super.shutdown();
	}
}
