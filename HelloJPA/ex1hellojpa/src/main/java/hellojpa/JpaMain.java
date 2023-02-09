package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    /*
    * Member 테스트 코드 (단방향 연관관계)
    * */
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team); // 영속상태가 되면 PK값이 셋팅되고 영속됨

            Member member = new Member();
            member.setUsername("member1");
            member.setTeam(team);
            em.persist(member);

            Member findMember = em.find(Member.class, member.getId());
            Team findTeam = findMember.getTeam();
            System.out.println("findTeam = " + findTeam.getName());

            tx.commit();
        } catch (Exception e){
            tx.rollback();
        } finally {
            em.clear();
        }

        emf.close();


        /*
        * MemberV1 테스트 코드
        * */
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
//
//        EntityManager em = emf.createEntityManager();
//
//        // 트랜잭션 시작
//        EntityTransaction tx = em.getTransaction();
//        tx.begin();
//
//
//        try {
//            MemberV1 member1 = new MemberV1();
//            member1.setUsername("A");
//            MemberV1 member2 = new MemberV1();
//            member2.setUsername("B");
//            MemberV1 member3 = new MemberV1();
//            member3.setUsername("C");
//
//            System.out.println("===============");
//            em.persist(member1); // 1,51
//            em.persist(member2); // memory
//            em.persist(member3); // memory
//
//            System.out.println("member1 = " + member1.getId());
//            System.out.println("member2 = " + member2.getId());
//            System.out.println("member3 = " + member3.getId());
//            System.out.println("===============");
//
//
////            Member findMember = em.find(Member.class, 1L);
////            em.remove(findMember); // 삭제
//
////            findMember.setName("HelloJPA"); // update가 되어짐
//
////            List<Member> result = em.createQuery("select m from Member as m", Member.class)
////                    .setFirstResult(5)
////                    .setMaxResults(8)
////                    .getResultList();
////
////            for (Member member : result) {
////                System.out.println("member.name = " + member.getName());
////            }
//
////            System.out.println("findMember.id = " + findMember.getId());
////            System.out.println("findMember.name = " + findMember.getName());
////
////            em.flush();
//            // 비영속 상태
////            Member member = new Member();
////            member.setId(100L);
////            member.setName("HelloJPA");
//
//            // 영속 상태
////            em.persist(findMember);
//            tx.commit();
//        } catch (Exception e){
//            tx.rollback();
//        } finally {
//            em.close();
//        }
//
//        emf.close();
    }
}
