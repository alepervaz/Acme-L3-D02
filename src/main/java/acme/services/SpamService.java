
package acme.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.repositories.SpamRepository;

@Service
public class SpamService {

	@Autowired
	private SpamRepository repository;


	public boolean validateTextInput(final String input) {
		String spamWords;
		double spamThreshold;

		spamWords = this.repository.findSpamWords();
		spamThreshold = this.repository.findSpamThreshold();

		// final SpamFilter spamFilter = new SpamFilter(spamWords, spamThreshold);
		//  return !spamFilter.isSpam(input);
		return false;
	}
}
