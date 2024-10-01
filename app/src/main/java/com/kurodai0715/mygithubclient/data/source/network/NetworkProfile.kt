package com.kurodai0715.mygithubclient.data.source.network

import com.squareup.moshi.Json

data class NetworkProfile(
    val login: String,
    val id: Int,
    @Json(name = "node_id") val nodeId: String,
    @Json(name = "avatar_url") val avatarUrl: String,
    @Json(name = "gravatar_id") val gravatarId: String?,
    val url: String,
    @Json(name = "html_url") val htmlUrl: String,
    @Json(name = "followers_url") val followersUrl: String,
    @Json(name = "following_url") val followingUrl: String,
    @Json(name = "gists_url") val gistsUrl: String,
    @Json(name = "starred_url") val starredUrl: String,
    @Json(name = "subscriptions_url") val subscriptionsUrl: String,
    @Json(name = "organizations_url") val organizationsUrl: String,
    @Json(name = "repos_url") val reposUrl: String,
    @Json(name = "events_url") val eventsUrl: String,
    @Json(name = "received_events_url") val receivedEventsUrl: String,
    val type: String,
    @Json(name = "site_admin") val siteAdmin: Boolean,
    val name: String?,
    val company: String?,
    val blog: String?,
    val location: String?,
    val email: String?,
    @Json(name = "notification_email") val notificationEmail: String?,
    val hireable: Boolean?,
    val bio: String?,
    @Json(name = "twitter_username") val twitterUsername: String?,
    @Json(name = "public_repos") val publicRepos: Int,
    @Json(name = "public_gists") val publicGists: Int,
    val followers: Int,
    val following: Int,
    @Json(name = "created_at") val createdAt: String,
    @Json(name = "updated_at") val updatedAt: String,
    @Json(name = "private_gists") val privateGists: Int?,
    @Json(name = "total_private_repos") val totalPrivateRepos: Int?,
    @Json(name = "owned_private_repos") val ownedPrivateRepos: Int?,
    @Json(name = "disk_usage") val diskUsage: Int?,
    val collaborators: Int?,
    @Json(name = "two_factor_authentication") val twoFactorAuthentication: Boolean?,
    val plan: NetworkPlan?,
    @Json(name = "suspended_at") val suspendedAt: String?,
    @Json(name = "business_plus") val businessPlus: Boolean?,
    @Json(name = "ldap_dn") val ldapDn: String?
)

data class NetworkPlan(
    val name: String,
    val space: Int,
    @Json(name = "private_repos") val privateRepos: Int,
    val collaborators: Int
)