package com.kurodai0715.mygithubclient.data.source.network

import com.squareup.moshi.Json
import retrofit2.Response
import java.net.URI
import java.util.Date

sealed class UserReposApiResponse{
    data class Success(val response: Response<List<NetworkUserRepo>>): UserReposApiResponse()
    data class ContentsError(val response: Response<*>?): UserReposApiResponse()
    data class NoResponseError(val error: Exception): UserReposApiResponse()
}

data class NetworkUserRepo(
    @Json(name = "id") val id: Int,
    @Json(name = "node_id") val nodeId: String,
    @Json(name = "name") val name: String,
    @Json(name = "full_name") val fullName: String,
    @Json(name = "license") val license: Any?,
    @Json(name = "forks") val forks: Int,
    @Json(name = "permissions") val permissions: Permissions? = null,
    @Json(name = "owner") val owner: Owner,
    @Json(name = "private") val private: Boolean = false,
    @Json(name = "html_url") val htmlUrl: URI,
    @Json(name = "description") val description: String?,
    @Json(name = "fork") val fork: Boolean,
    @Json(name = "url") val url: URI,
    @Json(name = "archive_url") val archiveUrl: String,
    @Json(name = "assignees_url") val assigneesUrl: String,
    @Json(name = "blobs_url") val blobsUrl: String,
    @Json(name = "branches_url") val branchesUrl: String,
    @Json(name = "collaborators_url") val collaboratorsUrl: String,
    @Json(name = "comments_url") val commentsUrl: String,
    @Json(name = "commits_url") val commitsUrl: String,
    @Json(name = "compare_url") val compareUrl: String,
    @Json(name = "contents_url") val contentsUrl: String,
    @Json(name = "contributors_url") val contributorsUrl: URI,
    @Json(name = "deployments_url") val deploymentsUrl: URI,
    @Json(name = "downloads_url") val downloadsUrl: URI,
    @Json(name = "events_url") val eventsUrl: URI,
    @Json(name = "forks_url") val forksUrl: URI,
    @Json(name = "git_commits_url") val gitCommitsUrl: String,
    @Json(name = "git_refs_url") val gitRefsUrl: String,
    @Json(name = "git_tags_url") val gitTagsUrl: String,
    @Json(name = "git_url") val gitUrl: String,
    @Json(name = "issue_comment_url") val issueCommentUrl: String,
    @Json(name = "issue_events_url") val issueEventsUrl: String,
    @Json(name = "issues_url") val issuesUrl: String,
    @Json(name = "keys_url") val keysUrl: String,
    @Json(name = "labels_url") val labelsUrl: String,
    @Json(name = "languages_url") val languagesUrl: URI,
    @Json(name = "merges_url") val mergesUrl: URI,
    @Json(name = "milestones_url") val milestonesUrl: String,
    @Json(name = "notifications_url") val notificationsUrl: String,
    @Json(name = "pulls_url") val pullsUrl: String,
    @Json(name = "releases_url") val releasesUrl: String,
    @Json(name = "ssh_url") val sshUrl: String,
    @Json(name = "stargazers_url") val stargazersUrl: URI,
    @Json(name = "statuses_url") val statusesUrl: String,
    @Json(name = "subscribers_url") val subscribersUrl: URI,
    @Json(name = "subscription_url") val subscriptionUrl: URI,
    @Json(name = "tags_url") val tagsUrl: URI,
    @Json(name = "teams_url") val teamsUrl: URI,
    @Json(name = "trees_url") val treesUrl: String,
    @Json(name = "clone_url") val cloneUrl: String,
    @Json(name = "mirror_url") val mirrorUrl: URI?,
    @Json(name = "hooks_url") val hooksUrl: URI,
    @Json(name = "svn_url") val svnUrl: URI,
    @Json(name = "homepage") val homepage: URI?,
    @Json(name = "language") val language: String?,
    @Json(name = "forks_count") val forksCount: Int,
    @Json(name = "stargazers_count") val stargazersCount: Int,
    @Json(name = "watchers_count") val watchersCount: Int,
    @Json(name = "size") val size: Int,
    @Json(name = "default_branch") val defaultBranch: String,
    @Json(name = "open_issues_count") val openIssuesCount: Int,
    @Json(name = "is_template") val isTemplate: Boolean? = false,
    @Json(name = "topics") val topics: List<String>? = null,
    @Json(name = "has_issues") val hasIssues: Boolean = true,
    @Json(name = "has_projects") val hasProjects: Boolean = true,
    @Json(name = "has_wiki") val hasWiki: Boolean = true,
    @Json(name = "has_pages") val hasPages: Boolean,
    @Json(name = "has_downloads") val hasDownloads: Boolean = true,
    @Json(name = "has_discussions") val hasDiscussions: Boolean? = false,
    @Json(name = "archived") val archived: Boolean = false,
    @Json(name = "disabled") val disabled: Boolean,
    @Json(name = "visibility") val visibility: String? = "public",
    @Json(name = "pushed_at") val pushedAt: Date,
    @Json(name = "created_at") val createdAt: Date,
    @Json(name = "updated_at") val updatedAt: Date,
    @Json(name = "allow_rebase_merge") val allowRebaseMerge: Boolean? = true,
    @Json(name = "temp_clone_token") val tempCloneToken: String? = null,
    @Json(name = "allow_squash_merge") val allowSquashMerge: Boolean? = true,
    @Json(name = "allow_auto_merge") val allowAutoMerge: Boolean? = false,
    @Json(name = "delete_branch_on_merge") val deleteBranchOnMerge: Boolean? = false,
    @Json(name = "allow_update_branch") val allowUpdateBranch: Boolean? = false,
    @Json(name = "use_squash_pr_title_as_default") val useSquashPrTitleAsDefault: Boolean? = false,
    /**
     * The default value for a squash merge commit title:
     *
     * - `PR_TITLE` - default to the pull request's title.
     * - `COMMIT_OR_PR_TITLE` - default to the commit's title (if only one commit) or the pull request's title (when more than one commit).
     *
     */
    @Json(name = "squash_merge_commit_title")
    val squashMergeCommitTitle: SquashMergeCommitTitle? = null,
    /**
     * The default value for a squash merge commit message:
     *
     * - `PR_BODY` - default to the pull request's body.
     * - `COMMIT_MESSAGES` - default to the branch's commit messages.
     * - `BLANK` - default to a blank commit message.
     *
     */
    @Json(name = "squash_merge_commit_message")
    val squashMergeCommitMessage: SquashMergeCommitMessage? = null,
    /**
     * The default value for a merge commit title.
     *
     * - `PR_TITLE` - default to the pull request's title.
     * - `MERGE_MESSAGE` - default to the classic title for a merge message (e.g., Merge pull request #123 from branch-name).
     *
     */
    @Json(name = "merge_commit_title")
    val mergeCommitTitle: MergeCommitTitle? = null,
    /**
     * The default value for a merge commit message.
     *
     * - `PR_TITLE` - default to the pull request's title.
     * - `PR_BODY` - default to the pull request's body.
     * - `BLANK` - default to a blank commit message.
     *
     */
    @Json(name = "merge_commit_message")
    val mergeCommitMessage: MergeCommitMessage? = null,
    @Json(name = "allow_merge_commit") val allowMergeCommit: Boolean? = true,
    @Json(name = "allow_forking") val allowForking: Boolean? = null,
    @Json(name = "web_commit_signoff_required") val webCommitSignoffRequired: Boolean? = false,
    @Json(name = "open_issues") val openIssues: Int,
    @Json(name = "watchers") val watchers: Int,
    @Json(name = "master_branch") val masterBranch: String? = null,
    @Json(name = "starred_at") val starredAt: String? = null,
    @Json(name = "anonymous_access_enabled") val anonymousAccessEnabled: Boolean? = null,
)

/**
 * The default value for a merge commit message.
 *
 * - `PR_TITLE` - default to the pull request's title.
 * - `PR_BODY` - default to the pull request's body.
 * - `BLANK` - default to a blank commit message.
 *
 */
enum class MergeCommitMessage(private val value: String) {
    @Json(name = "PR_BODY")
    PR_BODY("PR_BODY"),

    @Json(name = "PR_TITLE")
    PR_TITLE("PR_TITLE"),

    @Json(name = "BLANK")
    BLANK("BLANK");

    override fun toString(): String {
        return this.value
    }

    fun value(): String {
        return this.value
    }

    companion object {
        private val CONSTANTS: MutableMap<String, MergeCommitMessage> = HashMap()

        init {
            for (c in entries) {
                CONSTANTS[c.value] = c
            }
        }

        fun fromValue(value: String): MergeCommitMessage {
            val constant = CONSTANTS[value]
            requireNotNull(constant) { value }
            return constant
        }
    }
}


/**
 * The default value for a merge commit title.
 *
 * - `PR_TITLE` - default to the pull request's title.
 * - `MERGE_MESSAGE` - default to the classic title for a merge message (e.g., Merge pull request #123 from branch-name).
 *
 */
enum class MergeCommitTitle(private val value: String) {
    @Json(name = "PR_TITLE")
    PR_TITLE("PR_TITLE"),

    @Json(name = "MERGE_MESSAGE")
    MERGE_MESSAGE("MERGE_MESSAGE");

    override fun toString(): String {
        return this.value
    }

    fun value(): String {
        return this.value
    }

    companion object {
        private val CONSTANTS: MutableMap<String, MergeCommitTitle> = HashMap()

        init {
            for (c in entries) {
                CONSTANTS[c.value] = c
            }
        }

        fun fromValue(value: String): MergeCommitTitle {
            val constant = CONSTANTS[value]
            requireNotNull(constant) { value }
            return constant
        }
    }
}


/**
 * The default value for a squash merge commit message:
 *
 * - `PR_BODY` - default to the pull request's body.
 * - `COMMIT_MESSAGES` - default to the branch's commit messages.
 * - `BLANK` - default to a blank commit message.
 *
 */
enum class SquashMergeCommitMessage(private val value: String) {
    @Json(name = "PR_BODY")
    PR_BODY("PR_BODY"),

    @Json(name = "COMMIT_MESSAGES")
    COMMIT_MESSAGES("COMMIT_MESSAGES"),

    @Json(name = "BLANK")
    BLANK("BLANK");

    override fun toString(): String {
        return this.value
    }

    fun value(): String {
        return this.value
    }

    companion object {
        private val CONSTANTS: MutableMap<String, SquashMergeCommitMessage> = HashMap()

        init {
            for (c in entries) {
                CONSTANTS[c.value] = c
            }
        }

        fun fromValue(value: String): SquashMergeCommitMessage {
            val constant = CONSTANTS[value]
            requireNotNull(constant) { value }
            return constant
        }
    }
}


/**
 * The default value for a squash merge commit title:
 *
 * - `PR_TITLE` - default to the pull request's title.
 * - `COMMIT_OR_PR_TITLE` - default to the commit's title (if only one commit) or the pull request's title (when more than one commit).
 *
 */
enum class SquashMergeCommitTitle(private val value: String) {
    @Json(name = "PR_TITLE")
    PR_TITLE("PR_TITLE"),

    @Json(name = "COMMIT_OR_PR_TITLE")
    COMMIT_OR_PR_TITLE("COMMIT_OR_PR_TITLE");

    override fun toString(): String {
        return this.value
    }

    fun value(): String {
        return this.value
    }

    companion object {
        private val CONSTANTS: MutableMap<String, SquashMergeCommitTitle> = HashMap()

        init {
            for (c in entries) {
                CONSTANTS[c.value] = c
            }
        }

        fun fromValue(value: String): SquashMergeCommitTitle {
            val constant = CONSTANTS[value]
            requireNotNull(constant) { value }
            return constant
        }
    }
}

data class Owner(
    @Json(name = "name") val name: String? = null,
    @Json(name = "email") val email: String? = null,
    @Json(name = "login") val login: String,
    @Json(name = "id") val id: Int,
    @Json(name = "node_id") val nodeId: String,
    @Json(name = "avatar_url") val avatarUrl: URI,
    @Json(name = "gravatar_id") val gravatarId: String,
    @Json(name = "url") val url: URI,
    @Json(name = "html_url") val htmlUrl: URI,
    @Json(name = "followers_url") val followersUrl: URI,
    @Json(name = "following_url") val followingUrl: String,
    @Json(name = "gists_url") val gistsUrl: String,
    @Json(name = "starred_url") val starredUrl: String,
    @Json(name = "subscriptions_url") val subscriptionsUrl: URI,
    @Json(name = "organizations_url") val organizationsUrl: URI,
    @Json(name = "repos_url") val reposUrl: URI,
    @Json(name = "events_url") val eventsUrl: String,
    @Json(name = "received_events_url") val receivedEventsUrl: URI,
    @Json(name = "type") val type: String,
    @Json(name = "site_admin") val siteAdmin: Boolean,
    @Json(name = "starred_at") val starredAt: String? = null,
    @Json(name = "user_view_type") val userViewType: String? = null,
)


data class Permissions(
    @Json(name = "admin") val admin: Boolean,
    @Json(name = "pull") val pull: Boolean,
    @Json(name = "triage") val triage: Boolean? = null,
    @Json(name = "push") val push: Boolean,
    @Json(name = "maintain") val maintain: Boolean? = null,
)