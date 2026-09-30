package com.mizcausevic.complianceeventledger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import java.net.BindException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.PortInUseException;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
        title = "Compliance Event Ledger",
        version = "0.1.0-demo",
        description = "Read-only API over six synthetic, in-memory events. Not an audit record or production ledger."
))
public class ComplianceEventLedgerApplication {

	public static void main(String[] args) {
		try {
			SpringApplication.run(ComplianceEventLedgerApplication.class, args);
		} catch (Exception exception) {
			if (hasPortBindingFailure(exception)) {
				String port = System.getenv().getOrDefault("PORT", "4311");
				System.out.println("Compliance Event Ledger could not start because port " + port + " is already in use.");
				System.out.println("Set a different port before running again, for example:");
				System.out.println("$env:PORT = \"4315\"");
				System.out.println(".\\mvnw.cmd spring-boot:run");
				System.exit(1);
			}
			throw exception;
		}
	}

	private static boolean hasPortBindingFailure(Throwable throwable) {
		Throwable current = throwable;
		while (current != null) {
			if (current instanceof PortInUseException || current instanceof BindException) {
				return true;
			}
			current = current.getCause();
		}
		return false;
	}
}
