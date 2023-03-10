
package acme.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@EqualsAndHashCode
public class Peep extends AbstractEntity {

	// Serialisation identifier

	protected static final long	serialVersionUID	= 1L;

	//Attributes

	@Temporal(TemporalType.TIMESTAMP)
	@Past
	@NotNull
	private Date				moment;

	@NotBlank
	@Length(max = 76)
	private String				title;

	@NotBlank
	@Length(max = 76)
	private String				nick;

	@NotBlank
	@Length(max = 101)
	private String				message;

	@Email
	private String				email;

	@URL
	private String				link;
}
