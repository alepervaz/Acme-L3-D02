
package acme.entities.course;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.framework.components.datatypes.Money;
import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Course extends AbstractEntity {
	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "[A-Z]{1,3}[0-9]{3}", message = "Letras de A-Z entre 1 y 3 veces, seguidas de tres numeros entre 0 y 9")
	protected String			code;

	@NotBlank
	@Length(max = 76)
	protected String			title;

	@NotBlank
	@Length(max = 101)
	protected String			abstractDescription;

	@NotNull
	protected Money				price;

	@OneToMany
	protected List<Lecture>		lectures;

	@NotNull
	protected Boolean			draftMode			= true;


	@Transient
	protected Boolean isHandsOn() {
		boolean res = false;
		final Integer countHandsOn = this.lectures.stream().filter(l -> l.getHandsOn() == true).collect(Collectors.toList()).size();
		if (countHandsOn >= this.lectures.size() / 2)
			res = true;
		return res;
	}
}
