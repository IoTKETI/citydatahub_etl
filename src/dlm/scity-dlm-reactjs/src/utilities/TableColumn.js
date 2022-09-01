import React from "react";
import {Button, Divider, Tag, Tooltip} from "antd";
import {NavLink} from "react-router-dom";

export const ALL_BASE_RULE_TABLE_COLUMNS = [
    {
        width: '100px',
        title: '#ID',
        dataIndex: 'id',
        key: 'id',
        fixed: 'left'
    },
    {
        width: '300px',
        title: 'Base Rule Name',
        dataIndex: 'base_rule_nm',
        key: 'base_rule_nm',
        fixed: 'left'
    },
    {
        width: '170px',
        title: 'Default Delete Cycle',
        dataIndex: 'dlft_delete_cycle',
        key: 'dlft_delete_cycle',
        render: text =>
            <span>{text}</span>
        ,
    },
    {
        width: '160px',
        title: 'Default Move Cycle',
        dataIndex: 'dlft_move_cycle',
        key: 'dlft_move_cycle',
    },
    {
        width: '385px',
        title: 'Description',
        dataIndex: 'desc',
        key: 'desc',
        render: (text, record) => {

            const limit = 100;
            const content = record.desc + "";

            if (content.length <= limit) {
                return <div>{content}</div>
            }

            const toShow = content.substring(0, limit) + "...";
            return (
                <div>
                    {toShow}
                </div>
            );
        }
    },
    {
        width: '385px',
        title: 'Memo',
        dataIndex: 'memo',
        key: 'memo',
        render: (text, record) => {

            const limit = 100;
            const content = record.memo + "";

            if (content.length <= limit) {
                return <div>{content}</div>
            }

            const toShow = content.substring(0, limit) + "...";
            return (
                <div>
                    {toShow}
                </div>
            );
        }
    },
    {
        width: '250px',
        title: 'Modifier ID',
        dataIndex: 'modr_id',
        key: 'modr_id',
    },
    {
        width: '200px',
        title: 'Modify Date',
        dataIndex: 'mod_dtm',
        key: 'mod_dtm',
    },
    {
        width: '250px',
        title: 'Register ID',
        dataIndex: 'regr_id',
        key: 'regr_id',
    },
    {
        width: '200px',
        title: 'Register Date',
        dataIndex: 'reg_dtm',
        key: 'reg_dtm',
    },
    {
        width: '150px',
        title: 'Action',
        key: 'action',
        fixed: 'right',

        render: (text, record) => {
            console.log(record);
            return (
                <span>
                    <Tooltip placement="top" title={"View Detail"}>

                         <NavLink to={{
                             pathname: '/base-rule/detail/' + record.id,
                             // hash: '#submit',
                             // search: '?id=' + record.componentId
                         }}><Button size={"small"} icon={'eye'}/></NavLink>

                    </Tooltip>

                    <Divider type="vertical"/>

                     <Tooltip placement="top" title={"Edit"}>
                         <NavLink to={{
                             pathname: '/base-rule/edit/' + record.id,
                             // hash: '#submit',
                             // search: '?id=' + record.componentId
                         }}> <Button size={"small"} type={"primary"} icon={"edit"}/></NavLink>
                    </Tooltip>

                    <Divider type="vertical"/>

                     <Tooltip placement="top" title={"Delete"}>
                        <Button size={"small"} type={"danger"} icon={"delete"}/>
                    </Tooltip>
                </span>
            );


        }
    },
];

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

export const ALL_RULE_TABLE_COLUMNS = [
    {
        width: '100px',
        title: '#ID',
        dataIndex: 'id',
        key: 'id',
        fixed: 'left'
    },
    {
        fixed: 'left',
        width: '300px',
        title: 'Rule Name',
        dataIndex: 'rule_nm',
        key: 'rule_nm',
    },
    {
        width: '170px',
        title: 'Delete Cycle',
        dataIndex: 'delete_cycle',
        key: 'delete_cycle',
        render: text =>
            <span>{text}</span>
        ,
    },
    {
        width: '160px',
        title: 'Move Cycle',
        dataIndex: 'move_cycle',
        key: 'move_cycle',
    },
    {

        width: '385px',
        title: 'Description',
        dataIndex: 'desc',
        key: 'desc',
        render: (text, record) => {

            const limit = 100;
            const content = record.desc + "";

            if (content.length <= limit) {
                return <div>{content}</div>
            }

            const toShow = content.substring(0, limit) + "...";
            return (
                <div>
                    {toShow}
                </div>
            );
        }
    },
    {
        width: '385px',
        title: 'Memo',
        dataIndex: 'memo',
        key: 'memo',
        render: (text, record) => {

            const limit = 100;
            const content = record.memo + "";

            if (content.length <= limit) {
                return <div>{content}</div>
            }

            const toShow = content.substring(0, limit) + "...";
            return (
                <div>
                    {toShow}
                </div>
            );
        }
    },
    {
        width: '250px',
        title: 'Modifier User',
        dataIndex: 'modr_id',
        key: 'modr_id',
    },
    {
        width: '200px',
        title: 'Modify Date',
        dataIndex: 'mod_dtm',
        key: 'mod_dtm',
    },
    {
        width: '250px',
        title: 'Register ID',
        dataIndex: 'regr_id',
        key: 'regr_id',
    },
    {
        width: '200px',
        title: 'Register Date',
        dataIndex: 'reg_dtm',
        key: 'reg_dtm',
    },
    {
        width: '400px',
        title: 'Base Rule Relations',
        key: 'base_rule_relations',
        dataIndex: 'base_rule_relations',
        render: tags => (
            <span>
                {tags.map(tag => {

                    return (
                        <Tag color="green" key={tag.id}>
                            {tag.name}
                        </Tag>

                    );
                })}
            </span>
        ),
    },
    {
        width: '150px',
        title: 'Action',
        key: 'action',
        fixed: 'right',
        render: (text, record) => {
            return (
                <span>
                    <Tooltip placement="top" title={"View Detail"}>

                         <NavLink to={{
                             pathname: '/rule/detail/' + record.id,
                             // hash: '#submit',
                             // search: '?id=' + record.componentId
                         }}><Button size={"small"} icon={'eye'}/></NavLink>

                    </Tooltip>

                    <Divider type="vertical"/>

                     <Tooltip placement="top" title={"Edit"}>
                         <NavLink to={{
                             pathname: '/rule/edit/' + record.id,
                             // hash: '#submit',
                             // search: '?id=' + record.componentId
                         }}> <Button size={"small"} type={"primary"} icon={"edit"}/></NavLink>
                    </Tooltip>

                    <Divider type="vertical"/>

                     <Tooltip placement="top" title={"Delete"}>
                        <Button size={"small"} type={"danger"} icon={"delete"}/>
                    </Tooltip>
                </span>
            );
        }
    },
];

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

export const ALL_POLICY_TABLE_COLUMNS = [
    {
        width: '100px',
        title: '#ID',
        dataIndex: 'id',
        key: 'id',
        fixed: 'left'
    },
    {
        fixed: 'left',
        width: '300px',
        title: 'Policy Name',
        dataIndex: 'policy_name',
        key: 'policy_name',
    },
    {
        width: '250px',
        title: 'Source Path Name',
        dataIndex: 'src_path_name',
        key: 'src_path_name',
        render: text =>
            <span>{text}</span>
        ,
    },
    {
        width: '200px',
        title: 'Source File Name',
        dataIndex: 'src_file_name',
        key: 'src_file_name',
    },
    {
        width: '250px',
        title: 'Destination Path Name',
        dataIndex: 'dest_path_name',
        key: 'dest_path_name',
    },
    {
        width: '200px',
        title: 'Destination File Name',
        dataIndex: 'dest_file_name',
        key: 'dest_file_name',
    },
    {
        width: '250px',
        title: 'Created User',
        dataIndex: 'created_user',
        key: 'created_user',
    },
    {
        width: '200px',
        title: 'Created Date',
        dataIndex: 'created_date',
        key: 'created_date',
    },
    {
        width: '250px',
        title: 'Modified User',
        dataIndex: 'modified_user',
        key: 'modified_user',
    },
    {
        width: '200px',
        title: 'Modified Date',
        dataIndex: 'modified_date',
        key: 'modified_date',
    },
    {
        width: '200px',
        title: 'Setup Date',
        dataIndex: 'setup_date',
        key: 'setup_date',
    },
    {
        width: '100px',
        title: 'Status',
        dataIndex: 'status',
        key: 'status',
        render: (text) => {
            let color = text === 'true' ? 'green' : 'volcano';

            return (
                <Tag color={color} key={text}>
                    {text.toUpperCase()}
                </Tag>
            );
        }
    },
    {
        width: '400px',
        title: 'Policy Rule Relations',
        key: 'policy_rule_relations',
        dataIndex: 'policy_rule_relations',
        render: policyRelations => (
            <span>
                {policyRelations.map(policyRelation => {

                    return (
                        <Tag color="geekblue" key={policyRelation.id}>
                            {policyRelation.name}
                        </Tag>

                    );
                })}
            </span>
        ),
    },

    {
        width: '150px',
        title: 'Action',
        key: 'action',
        fixed: 'right',
        render: (text, record) => {
            return (
                <span>
                    <Tooltip placement="top" title={"View Detail"}>
                         <NavLink to={{
                             pathname: '/policy/detail/' + record.id,
                         }}>
                             <Button size={"small"} icon={'eye'}/>
                         </NavLink>
                    </Tooltip>

                    <Divider type="vertical"/>

                     <Tooltip placement="top" title={"Edit"}>
                         <NavLink to={{
                             pathname: '/policy/edit/' + record.id,
                         }}>
                             <Button size={"small"} type={"primary"} icon={"edit"}/>
                         </NavLink>
                    </Tooltip>

                    <Divider type="vertical"/>

                     <Tooltip placement="top" title={"Delete"}>
                        <Button size={"small"} type={"danger"} icon={"delete"}/>
                    </Tooltip>
                </span>
            );
        }
    },
];
