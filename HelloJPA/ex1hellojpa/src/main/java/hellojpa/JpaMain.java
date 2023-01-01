package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        // 트랜잭션 시작
        EntityTransaction tx = em.getTransaction();
        tx.begin();


        try {
            Member member1 = new Member();
            member1.setUsername("A");
            Member member2 = new Member();
            member2.setUsername("B");
            Member member3 = new Member();
            member3.setUsername("C");

            System.out.println("===============");
            em.persist(member1); // 1,51
            em.persist(member2); // memory
            em.persist(member3); // memory

            System.out.println("member1 = " + member1.getId());
            System.out.println("member2 = " + member2.getId());
            System.out.println("member3 = " + member3.getId());
            System.out.println("===============");


//            Member findMember = em.find(Member.class, 1L);
//            em.remove(findMember); // 삭제

//            findMember.setName("HelloJPA"); // update가 되어짐

//            List<Member> result = em.createQuery("select m from Member as m", Member.class)
//                    .setFirstResult(5)
//                    .setMaxResults(8)
//                    .getResultList();
//
//            for (Member member : result) {
//                System.out.println("member.name = " + member.getName());
//            }

//            System.out.println("findMember.id = " + findMember.getId());
//            System.out.println("findMember.name = " + findMember.getName());
//
//            em.flush();
            // 비영속 상태
//            Member member = new Member();
//            member.setId(100L);
//            member.setName("HelloJPA");

            // 영속 상태
//            em.persist(findMember);
            tx.commit();
        } catch (Exception e){
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
