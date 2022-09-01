import React from "react";
import {NavLink} from "react-router-dom";
import {Icon, Tag} from "antd";
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome'
import {faExclamationTriangle, faPlay, faStop} from '@fortawesome/free-solid-svg-icons'


/*
*
* Define all tables skeleton for each Nifi component
* */

export const componentColumns = [
    {
        title: '#ID',
        dataIndex: 'id',
        key: 'id',
    },
    {
        title: 'Component Name',
        dataIndex: 'componentName',
        key: 'componentName',
        render: componentName => {
            let color = 'green';
            return (
                <span>
                            <Tag color={color} key={componentName}>
                                {componentName.toUpperCase()}
                            </Tag>
                        </span>
            )
        }
    },
    {
        title: 'Component ID',
        dataIndex: 'componentId',
        key: 'componentId',
    },
    {
        title: 'Component Type',
        dataIndex: 'componentType',
        key: 'componentType',
        render: componentType => {
            // let color = '#2db7f5';
            let color = 'blue';
            return (
                <span>
                            <Tag color={color} key={componentType}>
                                {componentType.toUpperCase()}
                            </Tag>
                        </span>
            )
        }
    },
    {
        title: 'Action',
        key: 'action',
        render: (text, record) => {
            console.log(record);
            let mainEndpoint = '';

            if (record.componentType === 'Processor')
                mainEndpoint = 'processors';
            else if (record.componentType === 'ProcessGroup')
                mainEndpoint = 'process-groups';
            else if (record.componentType === 'Connection')
                mainEndpoint = 'connections';

            return (
                <span>
                            <NavLink to={{
                                pathname: '/component-detail/' + mainEndpoint + '/' + record.componentId,
                                // hash: '#submit',
                                // search: '?id=' + record.componentId
                            }}><Icon className="primary" type="eye" theme="filled"/></NavLink>
                        </span>
            );
        }
    },
];

export const processGroupColumns = [
    {
        title: 'Name',
        dataIndex: 'name',
        key: 'name',
    },
    {
        title: 'Transferred',
        dataIndex: 'transferred',
        key: 'transferred'
    },
    {
        title: 'Read / Write',
        dataIndex: 'readWrite',
        key: 'readWrite',
    },
    {
        title: 'Input / Size',
        dataIndex: 'input',
        key: 'input',
    },
    {
        title: 'Output / Size',
        dataIndex: 'output',
        key: 'output',
    },
    {
        title: 'Sent / Size',
        dataIndex: 'sent',
        key: 'sent',
    },
    {
        title: 'Received / Size',
        dataIndex: 'received',
        key: 'received',
    },
];

export const remoteProcessGroupColumns = [
    {
        // width: '40%',
        title: 'Name',
        dataIndex: 'name',
        key: 'name',
    },
    {
        // width: '30%',
        title: 'Target URI',
        dataIndex: 'targetUri',
        key: 'targetUri'
    },
    {
        // width: '30%',
        title: 'Transmitting',
        dataIndex: 'transmitting',
        key: 'transmitting',
        render: runStatus => {
            let icon = null;
            if (runStatus.toLowerCase() === 'transmitting')
                icon = <FontAwesomeIcon color='rgb(209, 134, 134)' icon={faStop} size="sm"/>;
            else
                icon = <FontAwesomeIcon color='#cf9f5d' icon={faExclamationTriangle} size="sm"/>;
            return (
                <span>
                    {icon} {runStatus}
                </span>
            )
        }
    },
    {
        // width: '30%',
        title: 'Sent / Size',
        dataIndex: 'sent',
        key: 'sent',
    },
    {
        // width: '30%',
        title: 'Received / Size',
        dataIndex: 'received',
        key: 'received',
    },
];

export const processorColumns = [
    {
        // width: '40%',
        title: 'Name',
        dataIndex: 'name',
        key: 'name',
    },
    {
        // width: '30%',
        title: 'Type',
        dataIndex: 'type',
        key: 'type'
    },
    {
        // width: '30%',
        title: 'Process Group',
        dataIndex: 'processGroup',
        key: 'processGroup',
    },
    {
        // width: '30%',
        title: 'Run Status',
        dataIndex: 'runStatus',
        key: 'runStatus',
        render: runStatus => {
            let icon = null;
            if (runStatus === 'Stopped')
                icon = <FontAwesomeIcon color='rgb(209, 134, 134)' icon={faStop} size="sm"/>;
            else if (runStatus === 'Running')
                icon = <FontAwesomeIcon color='rgb(125, 199, 160)' icon={faPlay} size="sm"/>;
            else
                icon = <FontAwesomeIcon color='#cf9f5d' icon={faExclamationTriangle} size="sm"/>;
            return (
                <span>
                    {icon} {runStatus}
                </span>
            )
        }
    },
    {
        // width: '30%',
        title: 'Input',
        dataIndex: 'input',
        key: 'input',
    },
    {
        // width: '30%',
        title: 'Output',
        dataIndex: 'output',
        key: 'output',
    },
    {
        // width: '30%',
        title: 'Read / Write',
        dataIndex: 'readWrite',
        key: 'readWrite',
    },
    {
        // width: '30%',
        title: 'Tasks / Time',
        dataIndex: 'tasksDuration',
        key: 'tasksDuration',
    },
    {
        title: 'Action',
        key: 'action',
        render: (text, record) => {
            console.log(record);
            return (
                <span>
                    <NavLink to={{
                        pathname: '/component-detail/processors/' + record.key,
                        // hash: '#submit',
                        // search: '?id=' + record.componentId
                    }}><Icon className="primary" type="eye" theme="filled"/></NavLink>
                </span>
            );
        }
    },
];

export const connectionColumns = [
    {
        width: '21%',
        title: 'Name',
        dataIndex: 'name',
        key: 'name',
    },
    {
        width: '20%',
        title: 'Source Name',
        dataIndex: 'sourceName',
        key: 'sourceName',
    },
    {
        width: '20%',
        title: 'Destination Name',
        dataIndex: 'destinationName',
        key: 'destinationName',
    },
    {
        width: '13%',
        title: 'In/Size 5min',
        dataIndex: 'input',
        key: 'input',
    },
    {
        width: '13%',
        title: 'Out/Size 5min',
        dataIndex: 'output',
        key: 'output',
    },
    {
        width: '13%',
        title: 'Queue/Size',
        dataIndex: 'queued',
        key: 'queued',
    },
    {
        title: 'Action',
        key: 'action',
        render: (text, record) => {
            console.log(record);
            return (
                <span>
                    <NavLink to={{
                        pathname: '/component-detail/connections/' + record.key,
                        // hash: '#submit',
                        // search: '?id=' + record.componentId
                    }}><Icon className="primary" type="eye" theme="filled"/></NavLink>
                </span>
            );
        }
    },
];

export const outputPortColumns = [
    {
        width: '40%',
        title: 'Name',
        dataIndex: 'name',
        key: 'name',
    },
    {
        width: '30%',
        title: 'Run Status',
        dataIndex: 'runStatus',
        key: 'runStatus',
        render: runStatus => {
            let icon = null;
            if (runStatus === 'Stopped')
                icon = <FontAwesomeIcon color='rgb(209, 134, 134)' icon={faStop} size="sm"/>;
            else if (runStatus === 'Running')
                icon = <FontAwesomeIcon color='rgb(125, 199, 160)' icon={faPlay} size="sm"/>;
            else
                icon = <FontAwesomeIcon color='#cf9f5d' icon={faExclamationTriangle} size="sm"/>;
            return (
                <span>
                    {icon} {runStatus}
                </span>
            )
        }
    },
    {
        width: '30%',
        title: 'Out / Size 5 min',
        dataIndex: 'output',
        key: 'output',
    },
];

export const inputPortColumns = [
    {
        width: '40%',
        title: 'Name',
        dataIndex: 'name',
        key: 'name',
    },
    {
        width: '30%',
        title: 'Run Status',
        dataIndex: 'runStatus',
        key: 'runStatus',
        render: runStatus => {
            let icon = null;
            if (runStatus === 'Stopped')
                icon = <FontAwesomeIcon color='rgb(209, 134, 134)' icon={faStop} size="sm"/>;
            else if (runStatus === 'Running')
                icon = <FontAwesomeIcon color='rgb(125, 199, 160)' icon={faPlay} size="sm"/>;
            else
                icon = <FontAwesomeIcon color='#cf9f5d' icon={faExclamationTriangle} size="sm"/>;
            return (
                <span>
                    {icon} {runStatus}
                </span>
            )
        }
    },
    {
        width: '30%',
        title: 'In / Size 5 min',
        dataIndex: 'input',
        key: 'input',
    },
];

export const bulletinsColumns = [
    {
        width: '15%',
        title: 'Timestamp',
        dataIndex: 'timestamp',
        key: 'timestamp',
    },
    {
        width: '20%',
        title: 'Category',
        dataIndex: 'category',
        key: 'category',

    },
    {
        width: '10%',
        title: 'Level',
        dataIndex: 'level',
        key: 'level',
        render: level => {
            let icon = null;
            if (level === 'warning'.toUpperCase())
                icon = '#cf9f5d';
            else if (level === 'error'.toUpperCase())
                icon = 'rgb(209, 134, 134)';
            else
                icon = 'rgb(125, 199, 160)';
            return (
                <span style={{color: icon}}>
                   {level}
                </span>
            )
        }
    },
    {
        width: '55%',
        title: 'Message',
        dataIndex: 'message',
        key: 'message',
    },
];

export const bucketColumns = [
    {
        width: '28%',
        title: 'Name',
        dataIndex: 'name',
        key: 'name',
    },
    {
        width: '27%',
        title: 'Identifier',
        dataIndex: 'identifier',
        key: 'identifier',
    },
    {
        width: '10%',
        title: 'Created Time',
        dataIndex: 'createdTimestamp',
        key: 'createdTimestamp',
    },
    {
        width: '14%',
        title: 'Allow Bundle Redeploy',
        dataIndex: 'allowBundleRedeploy',
        key: 'allowBundleRedeploy',
        render: (text, record) => {

            if (record.allowBundleRedeploy === true)
                return   <Tag color="#87d068">TRUE</Tag>;
            else
                return  <Tag color="#f50">FALSE</Tag>;
        }
    },
    {
        width: '12%',
        title: 'Allow Public Read',
        dataIndex: 'allowPublicRead',
        key: 'allowPublicRead',
        render: (text, record) => {
            if (record.allowPublicRead === true)
                return   <Tag color="#87d068">TRUE</Tag>;
            else
                return  <Tag color="#f50">FALSE</Tag>;
        }
    },
    {
        title: 'Action',
        key: 'action',
        render: (text, record) => {
            console.log(record);
            return (
                <span>
                    <NavLink to={{
                        pathname: '/component-detail/connections/' + record.key,
                        // hash: '#submit',
                        // search: '?id=' + record.componentId
                    }}><Icon className="primary" type="eye" theme="filled"/></NavLink>
                </span>
            );
        }
    },
];

export const itemColumns = [
    {
        fixed: 'left',
        width: 400,
        title: 'Name',
        dataIndex: 'name',
        key: 'name',
    },
    /*{
        width: '21%',
        title: 'ID',
        dataIndex: 'identifier',
        key: 'identifier',
    },*/
    {
        width: '8%',
        title: 'Type',
        dataIndex: 'type',
        key: 'type',
    },
    {
        width: '7%',
        title: 'Version Count',
        dataIndex: 'versionCount',
        key: 'versionCount',
    },
    {
        width: '25%',
        title: 'Description',
        dataIndex: 'description',
        key: 'description',
    },
    {
        width: '10%',
        title: 'Created Times',
        dataIndex: 'createdTimestamp',
        key: 'createdTimestamp',
    },
    {
        width: '10%',
        title: 'Modified Times',
        dataIndex: 'modifiedTimestamp',
        key: 'modifiedTimestamp',
    },
    {
        width: '15%',
        title: 'Bucket Name',
        dataIndex: 'bucketName',
        key: 'bucketName',
    },
    /*{
        width: '21%',
        title: 'Bucket Id',
        dataIndex: 'bucketIdentifier',
        key: 'bucketIdentifier',
    },*/
    {
        title: 'Action',
        key: 'action',
        render: (text, record) => {
            console.log(record);
            return (
                <span>
                    <NavLink to={{
                        pathname: '/component-detail/connections/' + record.key,
                        // hash: '#submit',
                        // search: '?id=' + record.componentId
                    }}><Icon className="primary" type="eye" theme="filled"/></NavLink>
                </span>
            );
        }
    },
];
