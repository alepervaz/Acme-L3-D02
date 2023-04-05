
package acme.entities.sessionPracticum;

import java.util.Date;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.entities.practicum.Practicum;
import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class SessionPracticum extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "^[A-Z]{1,3}[0-9]{3}$")
	private String				code;

	@NotBlank
	@Length(max = 75)
	private String				title;

	@NotBlank
	@Length(max = 100)
	private String				abstractSession;

	@NotBlank
	@Length(max = 100)
	private String				description;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date				start;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date				end;

	@URL
	private String				link;

	private Boolean				additional;

	private Boolean 			confirmed;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------
	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Practicum			practicum;
}
