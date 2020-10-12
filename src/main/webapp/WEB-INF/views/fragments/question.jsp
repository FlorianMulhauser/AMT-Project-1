<%--
  Group:        RockAndStone (https://github.com/orgs/tweb-classroom/teams/rockandstone)
  Authors:      Bécaud Arthur, Egremy Bruno, Muller Robin & Teixeira Carvalho Stéphane
  Date:         23.09.2020
  Description:  Question fragment for StoneOverflow.
--%>
<div class="question flex border-b">
    <div class="w-full flex items-start px-4 py-4">
        <div class="w-full">
            <div class="flex items-center justify-between">
                <form action="/questionDetails" method="post">
                    <input name="questionUUID" value="${question.uuid}">
                    <button type="submit"><h2 class="questionTitleClass text-lg font-semibold text-gray-900 hover:underline -mt-1">${question.title}</h2></button>
                </form>
                <small class="text-sm text-gray-700">${question.formattedDate()} at ${question.formattedTime()}</small>
            </div>
            <p class="questionDescriptionClass mt-3 text-gray-700 text-sm">
                ${question.shortDescription()}
            </p>
            <div class="mt-4 text-gray-700">
                <span>${question.nbVotes}</span>
                <i class="mr-3 far fa-heart"></i>
                <span>${question.nbViews}</span>
                <i class="mr-3 far fa-eye"></i>
                <span class="inline-block text-sm font-semibold">- ${question.creator}</span>
            </div>
        </div>
    </div>
</div>
