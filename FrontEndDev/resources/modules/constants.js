angular.module("liztube.constants",[

]).constant('constants',{
    SERVER_ERROR : 'Une erreur inattendue est survenue. Si le problème persiste veuillez contacter l\'équipe de Liztube.',
    FILE_TYPE_ERROR : 'Veuillez sélectionner une vidéo de type "mp4"',
    FILE_SIZE_ERROR: 'La taille de la vidéo ne doit pas dépasser 500 Mo',
    NO_FILE_SELECTED: 'Aucune vidéo sélectionnée',
    FILE_SIZE_ALLOWED: 524288000,
    UPLOAD_DONE: "Téléchargement de la vidéo terminé ",
    DOWNLOAD_ON_AIR_FILE_NAME: "Téléchargement de la vidéo : ",
    NO_NOTIFICATIONS_FOUND: "Vous n'avez aucune notifications",
    UPDATE_PASSWORD_OK: "Votre mot de passe a bien était mis à jour",
    UPDATE_PASSWORD_NOK_OLD_PASSWORD: "Votre ancien mot de passe ne correspond pas",
    UPDATE_PROFILE_OK: "Votre profil à bien était mis à jour",
    UPDATE_VIDEO_DESCRIPTION_OK: "Votre vidéo à bien était mis à jour",
    NO_VIDEOS_FOUND: "Aucune vidéos trouvées",
    VIDEO_NOT_EXISTS: "La vidéo que vous voulez afficher n'éxiste pas"
});
