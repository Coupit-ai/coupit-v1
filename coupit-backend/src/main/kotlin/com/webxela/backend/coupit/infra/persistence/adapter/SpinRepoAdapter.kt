package com.webxela.backend.coupit.infra.persistence.adapter
//
//import com.webxela.backend.coupit.domain.model.SpinResult
//import com.webxela.backend.coupit.infrastructure.persistence.entity.SpinEntity
//import com.webxela.backend.coupit.infrastructure.persistence.mapper.SpinEntityMapper.toSpinResult
//import com.webxela.backend.coupit.infrastructure.persistence.repo.RewardJpaRepo
//import com.webxela.backend.coupit.infrastructure.persistence.repo.SpinJpaRepo
//import jakarta.transaction.Transactional
//import org.springframework.stereotype.Component
//
//@Component
//class SpinRepoAdapter(
//    private val spinJpaRepo: SpinJpaRepo,
//    private val rewardJpaRepo: RewardJpaRepo
//)  {
//
//     fun getSpinResultBySpinId(spinId: String): SpinResult? {
//        return spinJpaRepo.findSpinEntityBySpinId(spinId)?.toSpinResult()
//    }
//
//     fun getSpinResultBySessionId(sessionId: String): SpinResult? {
//        return spinJpaRepo.findSpinEntityBySessionId(sessionId)?.toSpinResult()
//    }
//
////    @Transactional
////     fun saveSpinResult(merchantId: String, rewardId: String, qrCode: String, sessionId: String): SpinResult {
////        val offer = rewardJpaRepo.findByRewardId(rewardId)
////            ?: throw IllegalStateException("Offer not found in database")
////
////        val spinEntity = SpinEntity(
////            id = merchantId,
////            reward = offer,
////            qrCode = qrCode,
////            session = sessionId
////        )
////
////        return spinJpaRepo.save(spinEntity).toSpinResult()
////    }
//
//    @Transactional
//     fun markSpinAsClaimed(spinId: String): Boolean {
//        return when (spinJpaRepo.markSpinAsClaimed(spinId)) {
//            0 -> false
//            1 -> true
//            else -> throw IllegalStateException("Unexpected state: Multiple rows marked as claimed")
//        }
//    }
//
//    @Transactional
//     fun deleteSpinResult(sessionId: String): Boolean {
//        return when(spinJpaRepo.deleteBySessionId(sessionId)) {
//            0 -> false
//            1 -> true
//            else -> throw IllegalStateException("Unexpected state: Multiple rows are deleted")
//        }
//    }
//}