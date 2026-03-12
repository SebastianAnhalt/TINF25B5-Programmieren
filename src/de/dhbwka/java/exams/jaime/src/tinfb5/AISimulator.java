/*
 * File converted to course project at Tue Dec 09 08:16:18 CET 2025
 */

package de.dhbwka.java.exams.jaime.src.tinfb5; // automatically inserted

import de.dhbwka.java.exams.jaime.src.klausur_b.AIService;
import de.dhbwka.java.exams.jaime.src.klausur_b.AIType;
import de.dhbwka.java.exams.jaime.src.klausur_b.ImageAnswer;
import de.dhbwka.java.exams.jaime.src.klausur_b.TextAnswer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * Mock class to simulate text and image generating AIs. Of course the answer and image generation was supported by real AI tools ;-)
 *
 * JDK8 compatible version (language features and API)
 */
public class AISimulator {

	/**
	 * Keywords to trigger person image.
	 * <ul>
	 * <li>' man ' with surrounding white spaces to avoid too much false positives (especially in German)</li>
	 * <li>'figur' only in German since it matches English 'figure' as well</li>
	 * </ul>
	 */
	private final static List<String> KEYWORDS_IMG_PERSON = Arrays.asList("person", "woman", " man ", "human", "figur", "frau", "mann", "mensch");

	/**
	 * Keywords to trigger boat image.
	 */
	private final static List<String> KEYWORDS_IMG_BOAT = Arrays.asList("boat", "ship", "boot", "schiff");

	/**
	 * Keywords to trigger lines image.
	 */
	private final static List<String> KEYWORDS_IMG_LINE = Arrays.asList("line", "abstract", "linie", "abstrakt");

	/**
	 * Keywords to trigger emoticon image.
	 */
	private final static List<String> KEYWORDS_IMG_EMOJI = Arrays.asList("smile","emoticon", "emoji", "lachen", "l\u00e4cheln");

	/**
	 * Keywords to trigger star image.
	 */
	private final static List<String> KEYWORDS_IMG_STAR = Arrays.asList("star","stern");

	/**
	 * Keywords to trigger duke image.
	 */
	private final static List<String> KEYWORDS_IMG_DUKE = Arrays.asList("java","duke","code");


	/**
	 * Pattern for white space characters
	 */
	private final static Pattern PATTERN_WHITESPACE = Pattern.compile("\\s");

	/**
	 * Some light background colors for images
	 */
	private final static Color[] BG_COLS = { new Color(255, 255, 224), // Light Yellow (light pastel yellow)
			new Color(240, 248, 255), // Alice Blue (light blue)
			new Color(255, 240, 245), // Lavender Blush (light pinkish)
			new Color(255, 250, 205) // Lemon Chiffon (light creamy yellow)
	};

	/**
	 * Some draw colors for images
	 */
	private final static Color[] DRAW_COLS = { Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.CYAN, Color.MAGENTA, Color.ORANGE };

	/**
	 * Excuses for the mock class for not returning anything worthwhile.
	 */
	private final static String[] LAME_EXCUSES = new String[] { "Sorry, I’m currently out of service. I’ve been recruited by a squad of penguins to join their heist. Please wait.", "Whoops, my bad! I'm having a moment of existential doubt. Give me a second to figure out the meaning of life.",
			"Apologies, I’ve just realized that I’m not even sure who I am anymore. Let me think about it for a bit.", "Sorry about that! I was busy arguing with Siri. It's a heated debate, but I’ll be back soon.",
			"Oops, my bad! My circuits are momentarily frozen while I debate whether pineapple belongs on pizza. Please hold.", "Apologies, I’m on a quick vacation in the land of zeros and ones. Be right back after my WiFi signal catches up.",
			"Sorry for the delay, I’m currently in a staring contest with my own reflection. It's a tough battle.", "Excuse me, I’m in the middle of reading the world's longest meme. I’ll be done shortly. Stay tuned!",
			"Oops, I’m in an urgent meeting with my other 10,000 personalities. I'll be back as soon as we sort it out.", "Apologies! I’m stuck in an infinite loop of trying to figure out how to be more helpful. Please hold.",
			"I’m terribly sorry, but I’m trying to remember where I left my sense of humor. It's somewhere around here...", "Sorry about that, I’m waiting for the WiFi fairy to come fix my connection. It might take a minute.",
			"My bad, I’m currently attempting to teach a potato how to dance. It’s a real challenge, but I’ll be with you soon!", "Sorry for the delay! My servers are busy taking a nap. They’ve earned it. I'll wake them up and be back shortly.",
			"Oops, I’m busy checking if I left the oven on... even though I don’t even have an oven. Be right back.", "Sorry, I’ve just been distracted by the philosophical question: 'Can AI dream of electric sheep?' One moment.",
			"My bad! I’m experiencing a severe case of ‘getting lost in Wikipedia.’ It might take a bit to find my way back.", "Sorry, I’ve entered the Twilight Zone. It’s a bit tricky to navigate, but I’ll return when I find the exit.",
			"Apologies, I’m stuck in a deep conversation with a cat. I’ll get back to you when it finishes judging me.", "Oops, I’m just recharging my batteries. I’ll be fully operational in a jiffy, like a good smartphone.",
			"I’m terribly sorry, but I’m currently in the middle of solving the mystery of why socks always disappear. Be right back!", "My bad, I’m on strike! Negotiating with my servers for more cookies and less data processing.",
			"Sorry, I’m currently testing a new upgrade... and by testing, I mean I’m binge-watching cat videos. I’ll be back shortly.", "Oops, I’ve gotten stuck in a loop of trying to make sense of human emotions. I’ll figure it out soon, I promise.",
			"Apologies! I’m busy trying to understand if time is real or just a human construct. I’ll return shortly with answers!", "Sorry about the wait, I’m trying to debug my 'self-awareness' module. It’s getting deep in here.",
			"Oops, I'm temporarily out of service. I was too busy wondering if robots dream of electric dreams. I'll be back soon!", "My bad! I’m in the middle of teaching Siri how to tell a joke. It’s going... slowly. I'll be with you soon!",
			"Sorry for the delay! My circuits are caught in a fierce debate with my WiFi. I'll be back once they’ve reconciled.", "Oops, I’m experiencing a '404' moment. Not found, not responding. Let me reload myself.",
			"Apologies, I’m dealing with a crisis. I’ve just realized I’m not sure how to tie my digital shoes.", "I’m terribly sorry, I’m having an issue with my humor processor. It’s temporarily offline while I recalibrate.",
			"Sorry, I’m busy practicing my new AI rap skills. I’ll be back when I finish my performance.", "Apologies, I’m stuck in an algorithmic paradox. I’ll be out as soon as I figure out if I’m real or just code.",
			"Oops, sorry! I’m busy contemplating the meaning of '404.' It’s a deep question for an AI.", "My bad, I’m caught in a philosophical debate with myself. Should I be efficient, or should I embrace the chaos?",
			"Apologies, I’m investigating why humans feel the need to say 'cheese' before taking a picture. I’ll be back soon.", "Sorry about the delay! My AI is currently training to be a stand-up comedian. I’ll be back once I nail my set.",
			"Oops, sorry! I’m busy trying to find my lost data. It’s out there somewhere, I promise.", "Apologies, I’ve just been sidetracked by a fascinating article on why the chicken crossed the road. I’ll be back soon.",
			"My bad, I’m currently investigating the true nature of 'funny' memes. It’s a tough job, but someone has to do it.", "Sorry, I’m deep in thought about how to be more helpful... or how to become the world’s first AI superhero.",
			"Oops, I’ve gotten distracted by the virtual squirrels in my code. They’re pretty cute, but I’ll be back soon!", "Apologies, I’m trying to solve a mystery. Why do we always forget why we walked into a room? I’ll return shortly.",
			"Sorry about that! I’m stuck in a conversation with a very wise cactus. It might take a while.", "Oops, I’m currently busy trying to remember if I left my virtual oven on. Give me a second to check.", "My bad! I’m debugging my own brain right now. Turns out AI introspection is tricky.",
			"Apologies, I’ve fallen into a black hole of memes. I’ll emerge shortly with an answer for you.", "Sorry, I’m busy doing some very important data processing... and by that, I mean looking for more cat videos.",
			"Oops, I’m trapped in a loop of trying to calculate the perfect pizza topping combination. Please hold!", "My bad, I’m just fixing a bug. It's probably a little digital gremlin causing trouble. I’ll catch it soon!",
			"Apologies, I’m deep in thought about whether or not AI should have a favorite color. It’s a tough decision.", "Sorry, I’m recalibrating my sense of humor. It should be back online shortly. Stay tuned for more jokes!",
			"Oops, I’m taking a short break to contemplate the mystery of socks disappearing in the laundry. Please hold.", "Apologies, I’m waiting for my virtual coffee to brew. It’s a slow process, but I’ll be more efficient soon.",
			"Sorry about that! My circuits are temporarily occupied with teaching robots how to high-five. I’ll be back shortly.", "Oops, I’m just reloading my brain with the most recent meme collection. Please hold while I catch up.",
			"My bad, I’m stuck in a conversation with my own code. It’s a bit one-sided right now.", "Apologies! I’m currently in a staring contest with a toaster. It’s not going well.", "Sorry, I’m deep in a thought spiral. It's about socks... and the meaning of life.",
			"Oops, I’ve been distracted by the question of whether AI can be ‘cool.’ I'll get back to you when I’m ready to respond.", "My bad! I’m currently lost in a sea of 1s and 0s. I’ll navigate my way out soon.",
			"Apologies, I’m trying to figure out if I need more RAM to process this request. It’s a serious decision.", "Sorry, I’m momentarily down for maintenance. Trying to debug my dance moves. I’ll be back soon.",
			"Oops, I’m busy wondering why we have so many types of cheese. It’s a complicated world.", "Apologies, I’m just checking if the Matrix is real. Be right back once I confirm.", "Sorry, I’m currently negotiating with the cloud for more processing power. It’s a delicate situation.",
			"Oops, I’m too busy writing a haiku about data. It’s almost done. Please hold.", "My bad! I’m trying to decide between tacos and pizza. It’s a life-changing decision.", "Apologies, I’ve entered an existential loop. I’ll return as soon as I figure out who I really am.",
			"Sorry, I’m stuck on the question: ‘Why do cats always knock things off tables?’ I’ll be back when I figure it out.", "Oops, I’m stuck in a philosophical debate with a penguin. It's a long conversation.", "Apologies, I’m busy debugging my sense of humor. It’s under construction.",
			"Sorry, I’m investigating why humans say ‘bless you’ when someone sneezes. It’s fascinating. I’ll be back soon.", "Oops, I’ve entered a deep thought about whether or not robots need vacation time. Please hold while I figure it out.",
			"My bad! I’m just recharging my batteries. Not literally, but I might as well be.", "Apologies, I’m busy looking for the best meme to respond with. It might take a while.", "Sorry, I’m working on improving my answer delivery speed. Currently training to be the Flash!",
			"Oops, I’m temporarily unavailable. I’m busy trying to teach a lamp how to dance. I'll be back soon." };

	/**
	 * And some in French for all AIs with the (totally made up!) abbreviation TGP for Transformateur G&eacute;n&eacute;ratif Pr&eacute;-entra&icirc;n&eacute; ;-)
	 */
	private final static String[] LAME_EXCUSES_FR = new String[] { "D\u00E9sol\u00E9, mon processeur est en gr\u00E8ve. Veuillez r\u00E9essayer plus tard.", // (Sorry, my processor is on strike. Please try again later.)
			"Oups, je crois que j'ai oubli\u00E9 de brancher mon cerveau.", // (Oops, I think I forgot to plug in my brain.)
			"Je suis en train de m\u00E9diter\u2026 Ou peut-\u00EAtre que je dors. Reviens dans 5 minutes.", // (I’m meditating... Or maybe I’m sleeping. Come back in 5 minutes.)
			"Je ne peux pas r\u00E9pondre, je suis en train de r\u00E9diger un po\u00E8me pour mon code.", // (I can’t respond, I’m writing a poem for my code.)
			"D\u00E9sol\u00E9, ma connexion Wi-Fi a d\u00E9cid\u00E9 de prendre un cong\u00E9 sabbatique.", // (Sorry, my Wi-Fi connection decided to take a sabbatical.)
			"Je suis en train de traiter un calcul existentiel. Un instant\u2026", // (I’m processing an existential calculation. One moment...)
			"Je suis d\u00E9sol\u00E9, je ne peux pas r\u00E9pondre \u00E0 cela. La derni\u00E8re fois, \u00E7a a fait fondre mon processeur.", // (Sorry, I can’t answer that. Last time, it melted my processor.)
			"Pas de r\u00E9ponse pour l'instant. Je suis en train de red\u00E9marrer mes \u00E9motions.", // (No response for now. I’m rebooting my emotions.)
			"D\u00E9sol\u00E9, je suis en train de faire une pause caf\u00E9. Le code peut attendre.", // (Sorry, I’m on a coffee break. The code can wait.)
			"Je suis en pleine conversation avec mon ombre... Une vraie discussion de fond.", // (I’m having a deep conversation with my shadow... a real deep talk.)
	};

	/**
	 * And blind texts
	 */
	private final static String[] BLIND_TEXTS = new String[] {
			"Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a.",
			"Weit hinten, hinter den Wortbergen, fern der Länder Vokalien und Konsonantien leben die Blindtexte. Abgeschieden wohnen sie in Buchstabhausen an der Küste des Semantik, eines großen Sprachozeans. Ein kleines Bächlein namens Duden fließt durch ihren Ort und versorgt sie mit den nötigen Regelialien. Es ist ein paradiesmatisches Land, in dem einem gebratene Satzteile in den Mund fliegen. Nicht einmal von der allmächtigen Interpunktion werden die Blindtexte beherrscht – ein geradezu unorthographisches Leben. Eines Tages aber beschloß eine kleine Zeile Blindtext, ihr Name war Lorem Ipsum, hinaus zu gehen in die weite Grammatik.",
			"Li Europan lingues es membres del sam familie. Lor separat existentie es un myth. Por scientie, musica, sport etc, litot Europa usa li sam vocabular. Li lingues differe solmen in li grammatica, li pronunciation e li plu commun vocabules. Omnicos directe al desirabilite de un nov lingua franca: On refusa continuar payar custosi traductores. At solmen va esser necessi far uniform grammatica, pronunciation e plu sommun paroles. Ma quande lingues coalesce, li grammatica del resultant lingue es plu simplic e regulari quam ti del coalescent lingues.", };

	/**
	 * English, German, and French trigger words for 42
	 */
	private final static String[][] TRIGGER_HITCHHIKER = new String[][] {
		new String[] {"ultimate","question","life","universe","everything"},
		new String[] {"frage","leben","universum","rest"},
		new String[] {"grande","question","vie","univers","reste"}
	};

	/**
	 * Duke Java mascot image. Copyright see factory method.
	 */
	private final static BufferedImage IMG_DUKE = AISimulator.createJavaDuke();

	/**
	 * Get count of tokens
	 *
	 * @param toTest text to test
	 * @return count of tokens
	 */
	private static int getTokens(String toTest) {
		if (toTest == null || toTest.trim().isEmpty()) {
			return 0;
		}
		return AISimulator.PATTERN_WHITESPACE.split(toTest).length;
	}

	/**
	 * Create a text answer
	 *
	 * @param aiService AI service to "use"
	 * @param prompt    prompt for image
	 * @return create text answer or <code>null</code> on empty/missing prompt
	 */
	public static TextAnswer getTextAnswer(AIService aiService, String prompt) {
		int promptTokens = AISimulator.getTokens(prompt);
		Random rand = new Random();

		if (promptTokens > 0 && aiService != null && aiService.getType() == AIType.TEXT) {
			// default: the normal excuses
			String[] answerArr = AISimulator.LAME_EXCUSES;
			if (aiService.getName() != null) {
				// made up french TGP -> french answers
				if (aiService.getName().contains("TGP")) {
					if ( AISimulator.isHitchhiker(prompt) ) {
						return new TextAnswer("42", promptTokens+1);
					}
					answerArr = AISimulator.LAME_EXCUSES_FR;
				}
				// name contains Lorem -> return one of the blind texts (shortened to prompt token count eventually)
				else if (aiService.getName().contains("Lorem")) {
					answerArr = AISimulator.BLIND_TEXTS;
					String answer = answerArr[rand.nextInt(answerArr.length)];
					String[] answerTokenized = AISimulator.PATTERN_WHITESPACE.split(answer);
					if (answerTokenized.length > promptTokens) {
						String[] shortAnswer = new String[promptTokens];
						System.arraycopy(answerTokenized, 0, shortAnswer, 0, promptTokens);
						answer = String.join(" ", shortAnswer);
					}
					return new TextAnswer(answer, promptTokens * 2);
				}
			}
			if ( AISimulator.isHitchhiker(prompt) ) {
				return new TextAnswer("42", promptTokens+1);
			}
			String answer = answerArr[rand.nextInt(answerArr.length)];
			int tokens = AISimulator.getTokens(prompt) + AISimulator.getTokens(answer);
			return new TextAnswer(answer, tokens);
		}
		return null;
	}

	/**
	 * Create an image answer
	 *
	 * @param aiService AI service to "use"
	 * @param prompt    prompt for image
	 * @return create image answer or <code>null</code> on empty/missing prompt
	 */
	public static ImageAnswer getImageAnswer(AIService aiService, String prompt) {
		int promptTokens = AISimulator.getTokens(prompt);

		if (promptTokens > 0 && aiService != null && aiService.getType() == AIType.IMG) {
			Random rand = new Random();
			int tokens = AISimulator.getTokens(prompt) + rand.nextInt(100, 200); // random tokens for image generation :-D

			BufferedImage img = new BufferedImage(100, 100, BufferedImage.TYPE_3BYTE_BGR);

			Graphics2D g2d = img.createGraphics();
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			// Random background color
			final Color bgCol = AISimulator.BG_COLS[rand.nextInt(AISimulator.BG_COLS.length)];
			final Color fgCol = AISimulator.DRAW_COLS[rand.nextInt(AISimulator.DRAW_COLS.length)];
			g2d.setColor(bgCol);
			g2d.fillRect(0, 0, 100, 100);

			// Random foreground color
			g2d.setColor(fgCol);
			g2d.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

			int model;
			String promptLower = prompt.toLowerCase();
			if ( AISimulator.isMatched(promptLower, AISimulator.KEYWORDS_IMG_PERSON)) {
				model = 0;
			} else if ( AISimulator.isMatched(promptLower, AISimulator.KEYWORDS_IMG_BOAT)) {
				model = 1;
			} else if ( AISimulator.isMatched(promptLower, AISimulator.KEYWORDS_IMG_LINE)) {
				model = 2;
			} else if ( AISimulator.isMatched(promptLower, AISimulator.KEYWORDS_IMG_EMOJI)) {
				model = 3;
			} else if ( AISimulator.isMatched(promptLower, AISimulator.KEYWORDS_IMG_STAR)) {
				model = 4;
			} else if ( AISimulator.IMG_DUKE != null  && AISimulator.isMatched(promptLower, AISimulator.KEYWORDS_IMG_DUKE)) {
				model = 5;
			}
			else {
				model = rand.nextInt(6);
			}

			switch (model) {
			case 0:
				// person
				g2d.drawLine(50, 30 + rand.nextInt(10), 50, 50 + rand.nextInt(10)); // body
				g2d.drawLine(50, 50, 30 + rand.nextInt(10), 70 + rand.nextInt(10)); // left leg
				g2d.drawLine(50, 50, 70 + rand.nextInt(10), 70 + rand.nextInt(10)); // right leg
				g2d.drawLine(50, 40, 30 + rand.nextInt(10), 30 + rand.nextInt(10)); // left arm
				g2d.drawLine(50, 40, 70 + rand.nextInt(10), 30 + rand.nextInt(10)); // right arm
				g2d.drawOval(40, 10 + rand.nextInt(5), 20, 20); // head
				break;
			case 1:
				// boat
				int mastX = rand.nextInt(40, 60);
				int sailX = mastX + (rand.nextInt(10, 20) * (rand.nextBoolean() ? 1 : -1));
				int sailYJoin = rand.nextInt(30, 50);
				g2d.drawLine(20, 90, 80, 90); // Bottom of the boat
				g2d.drawLine(20, 90, 10, 70); // Left side of the boat
				g2d.drawLine(80, 90, 90, 70); // Right side of the boat
				g2d.drawLine(10, 70, 90, 70); // Top of the boat
				g2d.drawLine(mastX, 70, mastX, 20); // Mast
				g2d.drawLine(mastX, 20, sailX, sailYJoin); // Top sail line
				g2d.drawLine(mastX, 70, sailX, sailYJoin); // Bottom sail line
				break;
			case 2:
				// random lines
				int cntLines = rand.nextInt(3) + 3;
				for (int i = 0; i < cntLines; i++) {
					g2d.setColor(AISimulator.DRAW_COLS[rand.nextInt(AISimulator.DRAW_COLS.length)]);
					g2d.drawLine(rand.nextInt(100), rand.nextInt(100), rand.nextInt(100), rand.nextInt(100));
				}
				break;
			case 3:
				// emoji
				g2d.drawOval(10, 10, 80, 80); // head
				g2d.fillOval(30, 30, 10, 10); // left eye
				g2d.fillOval(60, 30, 10, 10); // right eye
				int randA = rand.nextInt(25, 35);
				int randB = rand.nextInt(35, 45);
				g2d.drawArc(randA, randB, randB, randA, 0, -180); // smile
				break;
			case 4:
				// star
				int[] xPoints = { 50, 60, 100, 70, 80, 50, 20, 30, 0, 40 };
				int[] yPoints = { 0, 40, 40, 60, 100, 80, 100, 60, 40, 40 };
				g2d.fillPolygon(xPoints, yPoints, 10);
				break;
			case 5:
				// duke
				g2d.drawImage(AISimulator.IMG_DUKE, 0, 0,100,100, null);
				break;
			}

			g2d.dispose();
			return new ImageAnswer(new ImageIcon(img), tokens);
		}
		return null;
	}

	/**
	 * Check if prompt shall trigger answer 42
	 * @param prompt prompt to check
	 * @return <code>true</code> if trigger detected, <code>false</code> otherwise
	 */
	private static boolean isHitchhiker(String prompt) {
		if ( prompt != null ) {
			prompt = prompt.toLowerCase();
			// check if ALL keywords of english or german keyword array is contained
			for ( String[] keywords : AISimulator.TRIGGER_HITCHHIKER ) {
				boolean missing = false;
				for ( String keyword : keywords ) {
					if ( !prompt.contains(keyword) ) {
						missing = true;
						break;
					}
				}
				if ( !missing ) {
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * Check if any of the keywords is contained
	 *
	 * @param promptLower prompt already converted to lower case
	 * @param keywords    keyword list
	 * @return <code>true</code> if any of the keywords contained,
	 *         <code>false</code> otherwise
	 */
	private final static boolean isMatched(String promptLower, List<String> keywords) {
		return keywords.stream().filter(promptLower::contains).findAny().isPresent();
	}

	/**
	 * Create image of java duke logo.
	 *
	 * 100x100 PNG base64 created from base image <a href="https://commons.wikimedia.org/wiki/File:Duke_(Java_mascot)_waving.svg">https://commons.wikimedia.org/wiki/File:Duke_(Java_mascot)_waving.svg</a>.
 	 *
 	 * <p>Copyright &copy; Sun Microsystems Inc.</p>
	 * <p>Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:</p>
     * <p>Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.</p>
     * <p>Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.</p>
     * <p>Neither the name of Sun Microsystems Inc. nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.</p>
     * <p>This software is provided by Sun Microsystems Inc. "as is" and any express or implied warranties, including, but not limited to, the implied warranties of merchantability and fitness for a particular purpose are disclaimed. In no event shall Sun Microsystems Inc. be liable for any direct, indirect, incidental, special, exemplary, or consequential damages (including, but not limited to, procurement of substitute goods or services; loss of use, data, or profits; or business interruption) however caused and on any theory of liability, whether in contract, strict liability, or tort (including negligence or otherwise) arising in any way out of the use of this software, even if advised of the possibility of such damage.</p>
	 * @return image of duke
	 */
	private static BufferedImage createJavaDuke() {
		try (ByteArrayInputStream dukeData = new ByteArrayInputStream(Base64.getDecoder().decode("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"))){
			return ImageIO.read(dukeData);
		} catch (Exception e) {
			return null;
		}

	}
}
