
package acme.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Course extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------
	@NotBlank
	@Column(unique = true)
	private String				code;

	@NotBlank(message = "Title must not be blank")
	@Length(max = 76, message = "Title must be shorter than 76 characters")
	private String				title;

	@NotBlank
	@Length(max = 101, message = "Abstract must be shorter than 101 characters")
	private String				courseAbstract;

	@Min(0)
	private Double				retailPrice;

	@URL
	private String				furtherInformation;

	@NotNull()
	protected Approach			type;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------
	/*
	 * @Valid
	 * 
	 * @OneToMany(mappedBy = "lecture")
	 * private List<Le> lectures;
	 */

}
