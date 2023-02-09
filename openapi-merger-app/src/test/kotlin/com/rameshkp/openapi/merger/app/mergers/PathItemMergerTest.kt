package com.rameshkp.openapi.merger.app.mergers

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.mockk
import io.swagger.v3.oas.models.PathItem

class PathItemMergerTest: BehaviorSpec({
   given("for a path /a with 2 path items p1 and p2") {
       val path = "/a"
       val p1 = PathItem()
       p1.description = "p1 description"
       p1.get = mockk()
       val p2 = PathItem()
       p2.description = "p2 description"
       p2.post = mockk()

       `when`("PathMerger('/a', p1).merge(p2)") {
           PathItemMerger(path, p1).merge(p2)

           then("all non null property value of p1 should not be replaced") {
               p1.description shouldBe "p1 description"
           }
           then("all null property value of p1 should be replaced with corresponding p2 property value") {
               p1.post shouldNotBe null
           }
       }
   }
})