package hellojpa;

import javax.persistence.*;

@Entity // JPA사용하는 엔티티로 인식
//@Table(name = "MEMBER")
@SequenceGenerator(
        name = "MEMBER_SEQ_GENERATOR",
        sequenceName = "MEMBER_SEQ", // 맵핑할 데이터베이스 시퀀스 이름
        initialValue = 1, allocationSize = 50) // allocationSize -> call next 한번할때 50개씩 땡겨오도록
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "MEMBER_SEQ_GENERATOR")
    private Long id;

    @Column(name = "name", nullable = false)
    private String username;

    public Member() {
    }

    public Member(Long id, String name) {
        this.id = id;
        this.username = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }
}
