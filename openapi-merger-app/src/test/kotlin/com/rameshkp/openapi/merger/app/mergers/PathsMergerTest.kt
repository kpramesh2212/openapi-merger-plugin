package com.rameshkp.openapi.merger.app.mergers

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.mockk
import io.swagger.v3.oas.models.PathItem
import io.swagger.v3.oas.models.Paths

internal class PathsMergerTest : BehaviorSpec({
    given("2 paths object with same name and with same scheme") {
        `when`("merged using paths merger") {
            then("the result should contain one paths object with 1 schema") {
                val paths1 = Paths()
                val paths2 = Paths()

                val pathItem1 = PathItem()
                pathItem1.get = mockk()
                val pathItem2 = PathItem()
                pathItem1.get = mockk()

                paths1["/a"] = pathItem1
                paths2["/a"] = pathItem2


                val pathMerger = PathsMerger()
                pathMerger.merge(paths1)
                pathMerger.merge(paths2)

                val path = pathMerger.get()
                path.size shouldBe 1
                path["/a"] shouldNotBe null
                path["/a"]?.get shouldNotBe null
            }
        }
    }
    given("2 paths object with same name and with different schema") {
        `when`("merged using path merger") {
            then("the result should contain one paths object with 2 schema") {
                val paths1 = Paths()
                val paths2 = Paths()

                val pathItem1 = PathItem()
                pathItem1.get = mockk()
                val pathItem2 = PathItem()
                pathItem1.post = mockk()

                paths1["/a"] = pathItem1
                paths2["/a"] = pathItem2


                val pathMerger = PathsMerger()
                pathMerger.merge(paths1)
                pathMerger.merge(paths2)

                val path = pathMerger.get()
                path.size shouldBe 1
                path["/a"] shouldNotBe null
                path["/a"]?.get shouldNotBe null
                path["/a"]?.post shouldNotBe null
            }
        }
    }
})