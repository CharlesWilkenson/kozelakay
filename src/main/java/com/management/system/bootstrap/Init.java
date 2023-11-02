package com.management.system.bootstrap;

import com.management.system.entities.Authority;
import com.management.system.entities.Content;
import com.management.system.entities.Member;
import com.management.system.entities.Role;
import com.management.system.repository.ContentRepository;
import com.management.system.repository.MemberRepository;
import com.management.system.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;

@RequiredArgsConstructor
@Component
public class Init implements CommandLineRunner {
    private final ContentRepository contentRepository;
    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder bcryptEncoder;
    @Override
    public void run(String... args) throws Exception {
		/*
		 * Member member =
		 * Member.builder().email("charleswilkenson13@gmail.com").password("123").id(1L)
		 * .username("wilki").build();
		 * 
		 * member.setPassword(bcryptEncoder.encode(member.getPassword())); final
		 * HashSet<Role> roles = new HashSet<>(); Role role =
		 * roleRepository.findByName(Authority.MEMBER);
		 * 
		 * if (role == null) { role = Role.builder() .name(Authority.MEMBER) .build();
		 * roleRepository.save(role); } roles.add(role); member.setRoles(roles);
		 * memberRepository.save(member);
		 * 
		 * 
		 * Content content1 =
		 * Content.builder().authorId(member).title("CONSECTETUR ADIPISCING ONE").
		 * brief("Most Awesome Blue Lake With Snow\r\n" + "foreste").
		 * content("Some hotels and vacation rentals on Booking.com now offer reduced monthly rates on extended stays, helping you save more when you stay longe"
		 * ) .fileName("japan").build();
		 * 
		 * Content content2 =
		 * Content.builder().authorId(member).title("CONSECTETUR ADIPISCING TWO").
		 * brief("Most Awesome Blue Lake With Snow\r\n" + "foreste").
		 * content("Travel plans change – so can your bookings. Enjoy peace of mind when you book a property with free cancellation and flexible check-in"
		 * ) .fileName("ha-long-bay") .build();
		 * 
		 * Content content3 =
		 * Content.builder().authorId(member).title("CONSECTETUR ADIPISCING THREE").
		 * brief("Most Awesome Blue Lake With Snow\r\n" + "foreste").
		 * content("from monthly rentals, hotels, and everything in between. Count on verified guest reviews t"
		 * ).fileName("uk").build();
		 * 
		 * Content content4 =
		 * Content.builder().authorId(member).title("CONSECTETUR ADIPISCING FOUR").
		 * brief("Most Awesome Blue Lake With Snow\r\n" + "foreste").
		 * content("Choose from monthly rentals, hotels, and everything in between. Count on verified guest reviews to pick a place you can call home."
		 * ) .fileName("flatten").build();
		 * 
		 * contentRepository.saveAll(List.of(content1, content2, content3, content4));
		 */
        
     //  List<Content> contents = contentRepository.findByAuthor_email("charleswilkenson13@gmail.com");
	 //  contents.stream().forEach(System.out::println);
    }
}
